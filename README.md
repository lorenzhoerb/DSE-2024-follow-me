# Distributed Systems Technologies

## Team

## Setup

### Install helm

Follow instructions [here](https://helm.sh/docs/intro/install/).

**Add helm repos:**

```shell
helm repo add datawire https://app.getambassador.io
helm repo add mongodb https://mongodb.github.io/helm-charts
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

### Build docker images

```shell
docker build -t inventory:latest inventory\
```

### Install helm chart

```shell
helm install dse helm\
```

To test I everything you can port-forward a service `kubectl port-forward service/inventory-service 8080:8080 -n dse`
and check the connection via `curl http://localhost:8080/api/inventory/`.

## Development

To run Spring Boot locally do the following:

Port forward mongodb: `kubectl port-forward inventory-mongodb-0 27017:27017 -n dse` (if you get an error related to 'not
primary' try the other replicas)  
Set the following env
variable: `connectionString.standard=mongodb://inventory-user:inventory-pass@localhost:27017/inventory?authSource=inventory`