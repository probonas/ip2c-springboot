# README

A spring microservice that can store and retrieve information on ips using IP2C.

## Setup

### Database

Using the latest version of docker and docker compose run:

```shell
 docker-compose up -d
```

to setup a docker container with Postgres 12.

To create the tables and seed the database:

```shell
cd src/main/resources/init_scripts/1.0
psql -U postgres -d ip2cspring -h 0.0.0.0 # password is 'postgres'
```

From within the Postgres CLI run:

```postgresql
\i 1.0.sql
```

### Run the application as standalone jar

To run the application run:

```shell
./mvnw spring-boot:run 
```

The application should now have connected to postgres by default.

## API

To query information for an ip, for example 46.255.255.254, run:

```shell
curl http://localhost:9090/api/info/46.255.255.254
```

### General Info

1. Framework: Spring v3
2. Database: PostgreSQL 12
3. JPA (entity framework):  Hibernate
5. Scheduling: Quartz
6. Cache: Hazelcast
7. Build System: maven
8. Java: Java18
