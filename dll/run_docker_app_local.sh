#!/bin/bash

cd ..

docker ps -a | awk '{ print $1,$2 }' | grep trading-app \
| awk '{print $1 }' | xargs -I {} docker container stop {}

docker ps -a | awk '{ print $1,$2 }' | grep trading-app \
| awk '{print $1 }' | xargs -I {} docker container rm {}

docker network disconnect trading-net trading-app

docker build -t trading-app .

docker run -d \
--restart unless-stopped \
-e "PSQL_URL=$PSQL_URL" \
-e "PSQL_USER=$PSQL_USER" \
-e "PSQL_PASSWORD=$PSQL_PASS" \
-e "IEX_PUB_TOKEN=$IEX_PUB_TOKEN" \
--name trading-app \
--network trading-net \
-p 8080:8080 -t trading-app

cd ./dll

docker logs -f trading-app
