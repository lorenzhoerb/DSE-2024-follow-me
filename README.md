# Distributed Systems Technologies

## Team

## Setup

### Install helm

Follow instructions [here](https://helm.sh/docs/intro/install/).

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

### Build docker images

```shell
docker build -t europe-north1-docker.pkg.dev/dse24-group-09/dse-repo/inventory:latest inventory
```

### Install helm chart

```shell
helm install dse helm\
```

To test if everything works you can check the service via `curl http://localhost/api/inventory/`.

## Development

To run Spring Boot locally do the following:

Port forward mongodb: `kubectl port-forward inventory-mongodb-0 27017:27017 -n dse` (if you get an error related to 'not
primary' try the other replicas)  
Set the following env
variable: `connectionString.standard=mongodb://inventory-user:inventory-pass@localhost:27017/inventory?authSource=inventory`

## Deployment on GKE

```shell
gcloud container clusters create dse-cluster --num-nodes=3 --machine-type e2-standard-4
gcloud container clusters get-credentials dse-cluster
```

Afterward execute the above commands. You can push containers as follows:

```shell
gcloud artifacts repositories create dse-repo --repository-format docker --location europe-north1

docker push europe-north1-docker.pkg.dev/dse24-group-09/dse-repo/inventory:latest
```

You can then get the external ip by running:

```shell
kubectl get svc -n emissary
```

You can then execute requests: `curl http://<external-ip>/api/inventory/`

### Cleanup

```
gcloud artifacts repositories delete dse-repo --location europe-north1
gcloud container clusters delete dse-cluster
```
