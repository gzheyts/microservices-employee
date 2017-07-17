# Microservices demo 

[Microservice architecture](http://microservices.io/) implementation example using [Spring Boot](https://projects.spring.io/spring-boot/) and [Docker](https://www.docker.com/).

## Running the tests and Deployment


```bash
mvn -f $PROJECT_ROOT/microservices-employee-rest/pom.xml test spring-boot:run #producer on :8080/api/employee
```

```bash
mvn -f $PROJECT_ROOT/microservices-employee-report/pom.xml spring-boot:run #consumer on :8082/api/employee/report
```
Available request mappings may be obtained from [Actuator](https://github.com/spring-projects/spring-boot/tree/master/spring-boot-actuator).


