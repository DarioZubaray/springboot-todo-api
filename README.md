# Springboot TODO api

### springboot-maven project

springboot starters modules:

- spring-boot-starter-web
- spring-boot-starter-security
- spring-boot-starter-validation
- spring-boot-starter-data-jpa
- spring-boot-starter-test
- spring-boot-devtools

### Models class

- uses annotations from [project lombok](https://projectlombok.org/)
- uses annotations from package **javax.validation.constraints**, The Spring Framework supports JSR-303 Bean Validation adapting it to Spring's Validator interface.

### Logging

Controllers and Services are annotated with *@SLF4J* tag from project Lombok.
The implementation is **logback** and it creates a file at:

```
	%PUBLIC%/springboot-todo/logs/spring-boot-logger.log
```

### Testing and coverage

Application use:

- Spring test
- Junit5
- Mockito

Coverage's report it's executed in the test' phase and it can be created with the command:

```
	mvn test
```


### swagger

swagger user interface can be found at:

```
	http://localhost:8080/swagger-ui/
```

### Spring profiles

You can found the 3 different profiles

- dev: Which creates an ArrayList in memory.
- test: Which uses an H2 database in memory.
- prod: Not implemented yet.


### H2 console

Database name, user and password can be found in application properties

```
	http://localhost:8080/h2-console/
```

### Spring Security

Application secure all endpoints with a **JWT** implementation with the exception of */authenticate* and ui for h2 and swagger
