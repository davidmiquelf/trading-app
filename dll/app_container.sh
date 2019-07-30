#!bin/bash

cd ..

docker ps -a | awk '{ print $1,$2 }' | grep trading-app \
| awk '{print $1 }' | xargs -I {} docker container stop {}

docker ps -a | awk '{ print $1,$2 }' | grep trading-app \
| awk '{print $1 }' | xargs -I {} docker container rm {}

docker network disconnect trading-net trading-app

docker build -t trading-app .

docker run \
-e "PSQL_URL=jdbc:postgresql://jrvs-psql:5432/jrvstrading" \
-e "PSQL_USER=postgres" \
-e "PSQL_PASSWORD=password" \
-e "IEX_PUB_TOKEN=$IEX_PUB_TOKEN" \
--name trading-app \
--network trading-net \
-p 8080:8080 -t trading-app
