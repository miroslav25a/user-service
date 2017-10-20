# user-service
A microservice for user data access

## Motivation
Qumu Technical test

## Installation
### Pre-request 
- Maven installation
- Mysql database server installation

### Build setup
Open a command window, as administrator, navigate to the root directory of the project,  and run 
> mvn clean install

### Database setup
Run the database script from the following files and create test schema with user table;
/user-service/src/main/resources/database/create_db.sql
/user-service/src/main/resources/database/user.sql

## Tests

> mvn test

## Usage
The application can be run with SpringBoot, there is no need to use a separate Tomcat.

The simplest way to run the application is

> mvn spring-boot:run

In the web browser type in the following URL;
http://localhost:8080/swagger-ui.html


