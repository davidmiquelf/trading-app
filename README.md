# Introduction
An online stock trading simlation REST API, built with a microservices architecture. Can be used to track accounts/positions/quotes and submit security orders. Could be connected to a front end stock trading website.
It's a MicroService implemented with SpringBoot. Uses a PSQL database, and accesses IEX market data through IEX API. Has Dockerfiles and scripts for setting up and deploying docker containers.

# Quick Start
## With Docker
 - Prequiresites: Java, Docker, CentOS 7  
 - Setup the proper environment variables. Edit `~/.bashrc` file to have them set up in every shell.
```$xslt
export IEX_PUB_TOKEN=<your-token>
export PSQL_USER=postgres
export PSQL_PASSWORD=password
export PSQL_URL="jdbc:postgresql://jrvs-psql:5432/jrvstrading"
```
 - Start docker, navigate to the project folder, then run the docker setup script.
```$xslt
$ systemctl start docker
$ cd dll
$ bash setup_docker.sh
```
 - To interact with the app, open a web browser and connect to 
 `http://localhost:8080/swagger-ui.html#/`
## Without docker
 - Run a local version of
# REST API Usage
## Swagger
What's swagger (1-2 sentences, you can copy from swagger docs). Why are we using it or who will benefit from it?
## Quote Controller
- High-level description for this controller. Where is market data coming from (IEX) and how did you cache the quote data (PSQL). Briefly talk about data from within your app
- briefly explain your endpoints in this controller
  - GET `/quote/dailyList`: list all securities that are available to trading in this trading system
  - PUT `/quote/iexMarketData`: Update all quotes from IEX which is an external market data source
## Trader Controller
- High-level description for trader controller(e.g. it can manage trader and account information. it can deposit and withdraw fund from a given account)
- briefly explain your endpoints in this controller
##Order Controller
- High-level description for this controller.
- briefly explain your endpoints in this controller
  - /order/MarketOrder: explain what is a market order, and how does your business logic work. 
## App controller
- GET `/health` to make sure SpringBoot app is up and running
## Optional(Dashboard controller)

# Architecture
- Draw a component diagram which contains controller, service, DAO, storage layers (you can mimic the diagram from the guide)
- briefly explain the following logic layers or components (3-5 sentences for each)
  - Controller 
  - Service
  - Dao
  - SpringBoot: webservlet/TomCat and IoC
  - PSQL and IEX

# Improvements
- at least 5 improvements