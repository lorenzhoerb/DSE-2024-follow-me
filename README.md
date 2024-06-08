# Distributed Systems Engineering

We suggest using IntelliJ with plugins such as `Go Template` and `Helm Values` for development.

## Project Structure

The project is structured into 3 parts:

- `backend/`: Contains the microservice backend which will be running as containers in kubernetes.
- `web/`: Contains the frontend which will be running as a container in kubernetes.
- `helm/`: Contains the helm chart used to deploy the project.

### Backend

The backend is build using Maven and Java 17 with spring boot.  
The `api` module is a library used by the other, while all other modules are microservice.  
The individual directories contain Dockerfiles. After compiling the Java source code you can build the images. This will
copy the jars into the image. See the [build section](README.md#build) for more details.

### Frontend

The frontend uses npm and Vue.  
The `web/` directory contains a Dockerfile. It is enough to simply build this without any additional commands. The
required files will automatically be copied and compiled in the image. The image serves the frontend on port 80 using
nginx.

To run the frontend locally you can execute `npm install` followed by `npm run dev`.  
You can modify the contents of `.env.local` to use a backend running somewhere else.

### Helm Chart

For deployment on Kubernetes [helm](https://helm.sh/) is used. Before starting here use `kubectl config get-contexts` to
check if you are using the correct cluster.

The chart itself is structured as follows:

- `mongodb/`: Contains the configuration to start 3 mongodb members in the cluster. MongoDB Community Operator is used
  for this and needs to be installed beforehand.
  After all 3 members are started secrets are created for all specified users. For example the beachcomb secret is
  called `mongodb-beachcomb-beachcomb-user` and contains a `connectionString.standard` field which can be used to
  connect to mongodb using this user.
- `rabbitmq/`: Contains the configuration to start 3 rabbitmq replicas in the cluster. RabbitMQ Cluster Operator is used
  for this and needs to be installed beforehand.  
  The secret `rabbitmq-custom-user` is created after installing the helm chart. It contains the
  fields `host`, `port`, `username` and `password` which can be used to connect to rabbitmq.
- Other Services: All other services are structured the same way.  
  There are 3 files:
    - `deployment.yaml` which creates the kubernetes deployment (possibly with additional env variables such as database
      connection information)
    - `service.yaml` which creates a service in Kubernetes. This will be used by the API gateway and other services for
      cluster internal communication.
    - `mapping.yaml` which create configuration for Ambassador, the chosen API gateway, to map hosts and paths to
      cluster internal services.

## Development

### Frontend

For development of the frontend you can start the whole instance in Kubernetes. Then modify `.env.local` to use the
services in the cluster.

### Backend

To run Spring Boot locally do the following:

Port forward mongodb: `kubectl port-forward mongodb-1 27017:27017 -n dse` (if you get an error related to 'not primary'
try the other replicas).  
Port forward rabbitmq: `kubectl port-forward rabbitmq-server-0 5672:5672 -n dse`.  
Set env variables `username=guest` and `password=guest` (e.g. via IntelliJ's run configuration) to allow connecting to
rabbitmq (required for each service).  
For starting the control-service locally you need to additionally port forward the
beachcomb-service: `kubectl port-forward beachcomb-service 8080:80 -n dse`.

You can now start all the spring boot applications outside the cluster. The frontend should in this case also run
outside the cluster (as the frontend inside the cluster is set to use the services inside the cluster).

## Build

The build itself us quite simple. First compile the backend using Maven. Afterward all images can be built:

```shell
cd backend
mvn clean package -DskipTests
cd ..
docker build -t europe-north1-docker.pkg.dev/dse24-group-09/dse-repo/beachcomb:latest backend/beachcomb-service
docker build -t europe-north1-docker.pkg.dev/dse24-group-09/dse-repo/control:latest backend/control-service
docker build -t europe-north1-docker.pkg.dev/dse24-group-09/dse-repo/datafeeder:latest backend/datafeeder
docker build -t europe-north1-docker.pkg.dev/dse24-group-09/dse-repo/event:latest backend/event-service
docker build -t europe-north1-docker.pkg.dev/dse24-group-09/dse-repo/inventory:latest backend/inventory-service
docker build -t europe-north1-docker.pkg.dev/dse24-group-09/dse-repo/web:latest web
```

## Testing

Each service has several functionality tests, which could be started with `mvn test` command.

## Local Deployment

**Add helm repos:**

```shell
helm repo add datawire https://app.getambassador.io
helm repo add mongodb https://mongodb.github.io/helm-charts
helm repo add bitnami https://charts.bitnami.com/bitnami
helm repo update
```

**Create namespace:**

```shell
kubectl create namespace dse
```

### Install Ambassador

```shell 
kubectl create namespace emissary
kubectl apply -f https://app.getambassador.io/yaml/emissary/3.9.1/emissary-crds.yaml
 
kubectl wait --timeout=90s --for=condition=available deployment emissary-apiext -n emissary-system
 
helm install emissary-ingress --namespace emissary datawire/emissary-ingress
kubectl -n emissary wait --for condition=available --timeout=90s deploy -lapp.kubernetes.io/instance=emissary-ingress
```

### Install MongoDB operator

```shell
helm install community-operator mongodb/community-operator --namespace dse
```

### Install RabbitMq operator

```shell
helm install rabbitmq-operator bitnami/rabbitmq-cluster-operator --namespace dse
```

### Install helm chart

```shell
helm install dse helm\
```

To test if everything works you can check by visiting `http://localhost/`.

## GCP Deployment

Setup cluster and docker image repository:

```shell
gcloud container clusters create dse-cluster --num-nodes=3 --machine-type e2-standard-4
gcloud container clusters get-credentials dse-cluster

gcloud artifacts repositories create dse-repo --repository-format docker --location europe-north1
```

Push images (after building them, see [here](README.md#build)):

```shell
docker push europe-north1-docker.pkg.dev/dse24-group-09/dse-repo/beachcomb:latest
docker push europe-north1-docker.pkg.dev/dse24-group-09/dse-repo/control:latest
docker push europe-north1-docker.pkg.dev/dse24-group-09/dse-repo/datafeeder:latest
docker push europe-north1-docker.pkg.dev/dse24-group-09/dse-repo/event:latest
docker push europe-north1-docker.pkg.dev/dse24-group-09/dse-repo/inventory:latest
docker push europe-north1-docker.pkg.dev/dse24-group-09/dse-repo/web:latest
```

Afterward execute the commands in the [local deployment section](README.md#local-deployment).

You can then get the external ip by running:

```shell
kubectl get svc -n emissary
```

To test if everything works you can check by visiting `http://<external-ip>/`

### Cleanup

After finishing, you can delete the cluster and repository on GCP:

```
gcloud artifacts repositories delete dse-repo --location europe-north1
gcloud container clusters delete dse-cluster
```

## API Documentations

Each service has REST API docs, which are made using swagger-ui and open-api modules. Documentation can be found for 
each service at `http://service_address/docs`.
