# Financial Portfolio

This system is under development and its main purpose is to serve as learning tool for different IT topics as Microservices, REST, React, Test Automation, and more. 

## Architecture

![Architecture](documents/architecture.png)

### Components Details

#### Portfolio Service

PORT: 8080

#### Stocks Service

PORT: 8081

```shell
# Starting Application
./gradlew :stocks-service:bootRun
```

## Test Strategy

This project contains different test approaches for learning purposes. They are:
1. **Unit Tests**: Each component has its own Unit Tests which are available on  `component-folder\src\test`
2. **Postman**: Postman is a great tool for testing APIs manually. Postman's files are available on `test-approaches/Postman` It allows the creation of test suites and execution on a CI Tool, however, this is not the goal of this project, since there are other better options for it.

## References:

### Spring: 

- https://spring.io/guides/tutorials/rest/

