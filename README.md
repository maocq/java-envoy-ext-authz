# JavaEnvoyExtAuthz

aws ecr-public get-login-password --region us-east-1 | docker login --username AWS --password-stdin public.ecr.aws/e0v3l5t6

NAME=java-envoy-ext-authz
TAG=test-one

gradle clean build
docker build -t $NAME:$TAG .
docker tag $NAME:$TAG public.ecr.aws/e0v3l5t6/general-repo:$NAME-$TAG
docker push public.ecr.aws/e0v3l5t6/general-repo:$NAME-$TAG

docker run --rm -it -p 8080:8080 public.ecr.aws/e0v3l5t6/general-repo:$NAME-$TAG
