# Kubernetes Cluster Resource Management

This README provides instructions for managing resources in a Kubernetes cluster using `kubectl`. Below are various commands for common tasks.

```bash
# Get Resources from the Kubernetes Cluster
kubectl get namespaces
kubectl get pods
kubectl get deployment
kubectl get services
kubectl get all
kubectl get all -o wide
kubectl delete all --all

# Manage Deployments and Pods
kubectl create deployment nginx --image=nginx
kubectl describe deployment nginx
kubectl describe pod nginx-77b4fdf86c-vkc4j
kubectl logs nginx-77b4fdf86c-vkc4j
kubectl exec -it nginx-77b4fdf86c-vkc4j -- /bin/bash
kubectl edit deployment nginx

# Deploy Resources to Kubernetes Cluster
# Navigate to the directory containing YAML files.
kubectl apply -f deploy.yml
kubectl apply -f svc.yml
kubectl apply -f ./folderName/
kubectl delete -f deploy.yml
kubectl delete -f svc.yml

# Namespace Management
kubectl create namespace my-namespace
kubectl get namespaces
kubectl apply -f deploy.yml -n my-namespace
kubectl get all -n my-namespace

# Access Services via Minikube
minikube service serviceName
minikube service eureka-lb
