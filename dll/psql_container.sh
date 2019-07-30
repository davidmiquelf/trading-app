#!bin/bash


docker ps -a | awk '{ print $1,$2 }' | grep jrvs-psql \
| awk '{print $1 }' | xargs -I {} docker container stop {}

docker ps -a | awk '{ print $1,$2 }' | grep jrvs-psql \
| awk '{print $1 }' | xargs -I {} docker container rm {}

cd ../psql

docker build -t jrvs-psql .

docker run --rm --name jrvs-psql \
-e POSTGRES_PASSWORD=password \
-e POSTGRES_DB=jrvstrading \
-e POSTGRES_USER=postgres \
--network trading-net \
-d -p 5432:5432 jrvs-psql

cd ../dll
