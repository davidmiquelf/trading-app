#!/bin/bash

echo "The following local vars must be set: "
echo "IEX_PUB_TOKEN"
echo "PSQL_USER"
echo "PSQL_PASSWORD"
echo "PSQL_HOST"
java -jar ./../target/trading-1.0-SNAPSHOT.jar
