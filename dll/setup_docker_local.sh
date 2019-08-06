#!/bin/bash

git pull

docker network create --driver bridge trading-net

source ./run_docker_app_local.sh
