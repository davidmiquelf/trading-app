#!/bin/bash

sudo systemctl enable docker

source ./network_bridge.sh

source ./app_container.sh
