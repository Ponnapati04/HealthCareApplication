# HealthCare Application

This microservice is developed with two entities Enrollee and Dependent to track the status of enrollees in a health care program. 

Enrollee will have ID, Name, BirthDate, ActivationStatus, PhoneNumber (Optional)
Dependent will have ID, Name, BirthDate

Enrollee can have zero or more dependents

## Getting Started

This application was developed using Spring Boot, Mysql, JPA and Hibernate.

## Requirements

Java - 1.8.x

Maven - 3.x.x

Mysql - 5.x.x

## Steps to Setup

**1. Configure MySQL Database

+ open `src/main/resources/application.properties`

+ change `spring.datasource.url` value as per your mysql installation. Currently the value provided is "healthcare"

Create Mysql database if there is no existing database**
```bash
create database healthcare
```

+ change `spring.datasource.username` and `spring.datasource.password` as per your mysql installation

**2. Build and run the app using maven**

```bash
mvn clean install
java -jar target/healthcare-0.0.1-SNAPSHOT.jar```

Alternatively, you can run the app without packaging it using -

```bash
mvn spring-boot:run
```

The app will start running at <http://localhost:8080>.

## Explore Rest APIs

The app defines following CRUD APIs.

Enrollees API
GET - /enrollees - Get All Enrollees
GET - /enrollees/{enrolleeId} - Get Enrollee by ID 
POST - /enrollees - Add Enrollee
PUT - /enrollees/{enrolleeId} - Modify Enrollee
DELETE - /enrollees/{enrolleeId} - Remove Enrollee

Dependents API
GET - /enrollees/{enrolleeId}/dependents - Get All Dependents
GET - /enrollees/{enrolleeId}/dependents/{dependentId} - Get Dependent by ID 
POST - /enrollees/{enrolleeId}/dependents - Add Dependent 
PUT - /enrollees/{enrolleeId}/dependents/{dependentId} - Modify Dependent 
DELETE - /enrollees/{enrolleeId}/dependents/{dependentId} - Remove Dependent 

You can test them using postman or any other rest client.

## Running the tests

To run the unit tests, call mvn test