# GET RESOURCES FROM THE KUBERNETES CLUSTER
###########################################################

# get all namespaces
kubectl get namespaces

# get all pods
kubectl get pods

# get all deployments
kubectl get deployment

# get all services
kubectl get services

###########################################################

# get every resource in the cluster
kubectl get all

# get more details about every resource in the cluster
kubectl get all -o wide

# delete every resource from the cluster
kubectl delete all --all

###########################################################

# create a nginx deployment
kubectl create deployment nginx --image=nginx

# get detail about a specific deployment
kubectl describe deployment nginx

# get detail about a specific pods
kubectl describe pod nginx-77b4fdf86c-vkc4j

# get logs of a pod
kubectl logs nginx-77b4fdf86c-vkc4j

# go interactive mode inside the pod
kubectl exec -it nginx-77b4fdf86c-vkc4j -- /bin/bash

# edit deployment configuration
kubectl edit deployment nginx

###########################################################

# deploy to kubernetes cluster after writing the yml
# go to the directory where the yml files are located

# apply deployment type resource to the cluster
kubectl apply -f deploy.yml

# apply service type resource to the cluster, this service will target pods defined in the confguration
kubectl apply -f svc.yml

# delete every resource configured in the deployment yml file
kubectl delete -f deploy.yml

# delete every resource configured in the service yml file
kubectl delete -f svc.yml