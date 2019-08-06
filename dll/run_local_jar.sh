#!/bin/bash

ps -u | awk '{ print $2,$13 }' | grep trading-1.0-SNAPSHOT.jar | awk '{print $1 }' | xargs -I {} kill {}

java -jar ./../target/trading-1.0-SNAPSHOT.jar
