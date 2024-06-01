# SkeletonApplication

This is a skeleton Java Spring boot multi-module application.

## Technologies Used:
1. JDK17
2. Spring boot 3.2
3. Cucumber 7
4. SonarQube
5. Postgres DB
6. Docker

## Roles of different modules: 

1. `aggregate-report` takes care of generating the code coverage report by gathering results from other modules.
2. `integration-tests` holds all the integration tests written in cucumber framework.
3. `service` module holds all the rest controllers and service/business logic.

## Prerequisites: 
1. JDK 17
2. Maven 3
3. Docker

## Steps to run the Project
1. Clone the project using git clone
2. Run the docker
3. Run `docker compose up -d`
4. Run `mvn clean install`

To access generated sonar report go to http://localhost:9000

Login into sonarqube using admin/admin default credentials
