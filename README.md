# City List

This is REST API for City List application

## How to run

Go to the root directory and run docker compose:
```sh
docker-compose up -d
```
Run project:
```sh
gradlew bootRun
```

It will be launched at localhost:8080 <br/>
Open in browser: http://localhost:8080/swagger-ui/index.html <br/>
For `update` service use the user: admin admin


## Improvements

* Some translation parameters could be added into Exception Handler
* User handling could be improved from in memory to entity
* Some tests could be added