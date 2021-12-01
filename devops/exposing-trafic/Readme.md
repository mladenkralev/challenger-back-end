### Ingress and metallb on microk8s and work with private ip

## Requirements
* Install microk8s and these plugins
    *  dashboard            # The Kubernetes dashboard
    *  dns                  # CoreDNS
    *  ha-cluster           # Configure high availability on the current node
    *  host-access          # Allow Pods connecting to Host services smoothly
    *  ingress              # Ingress controller for external access
    *  metallb              # Loadbalancer for your Kubernetes cluster
    *  metrics-server       # K8s Metrics Server for API access to service metrics
    *  rbac                 # Role-Based Access Control for authorisation
* Create deployment 
    * Expose service of type with type clusterIP like
    ```kubectl expose deployment/nginx-deployment --type ClusterIP --port 80```
* Install the ingress config in this foder and config if needed