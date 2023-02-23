cd src/infra

kubectl apply -f pg-secrets.yml
kubectl apply -f pg-service.yml
kubectl apply -f app-service.yml
kubectl apply -f pg-stateful.yml
kubectl apply -f app-deployment.yml
