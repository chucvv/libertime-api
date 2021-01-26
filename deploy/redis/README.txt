https://medium.com/@thanawitsupinnapong/setting-up-redis-in-kubernetes-with-helm-and-manual-persistent-volume-f1d52fa1919f

helm install redis-cluster \
  --set cluster.slaveCount=3 \
  --set password=libertime@2020 \
  --set securityContext.enabled=true \
  --set securityContext.fsGroup=2000 \
  --set securityContext.runAsUser=1000 \
  --set volumePermissions.enabled=true \
  --set master.persistence.enabled=true \
  --set slave.persistence.enabled=true \
  --set master.persistence.enabled=true \
  --set master.persistence.path=/data \
  --set master.persistence.size=8Gi \
  --set master.persistence.storageClass=manual \
  --set slave.persistence.enabled=true \
  --set slave.persistence.path=/data \
  --set slave.persistence.size=8Gi \
  --set slave.persistence.storageClass=manual \
stable/redis