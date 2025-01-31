#!/bin/#!/usr/bin/env bash
docker ps -a | awk '{ print $1,$2 }' | grep jrvs-psql \
| awk '{print $1 }' | xargs -I {} docker container stop {}

docker ps -a | awk '{ print $1,$2 }' | grep jrvs-psql \
| awk '{print $1 }' | xargs -I {} docker container rm {}

cd ../psql

docker build -t jrvs-psql .

docker run --name jrvs-psql \
--restart unless-stopped \
-e "POSTGRES_PASSWORD=$PSQL_PASSWORD" \
-e POSTGRES_DB=jrvstrading \
-e "POSTGRES_USER=$PSQL_USER" \
--network trading-net \
-d -p 5432:5432 jrvs-psql

cd ../dll

