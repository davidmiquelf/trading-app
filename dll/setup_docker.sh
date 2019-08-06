#!/bin/bash

git pull

docker network create --driver bridge trading-net

source ./run_docker_psql.sh

source ./run_docker_app.sh
