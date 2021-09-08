### What to do

* See the status of the project
* Build in k8s 
* Build in cloud provider

### Build

* ``` kind create cluster ```
* ``` helm install challenger devops/charts/chart -f devops/charts/overwrite/dev/helm-overwrites.yaml ```

set INGRESS_PORT=$(kubectl -n istio-system get service istio-ingressgateway -o jsonpath='{.spec.ports[?(@.name=="http2")].nodePort}')
set SECURE_INGRESS_PORT=$(kubectl -n istio-system get service istio-ingressgateway -o jsonpath='{.spec.ports[?(@.name=="https")].nodePort}')
set TCP_INGRESS_PORT=$(kubectl -n istio-system get service istio-ingressgateway -o jsonpath='{.spec.ports[?(@.name=="tcp")].nodePort}')
