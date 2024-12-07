# spring-jwt-service

## Introduction

### Token Properties
- secret key must be an HMAC hash string of 256 bits
- token is valid for 60 mins

### MySQL Docker Commands
```
docker run -d -e MYSQL_ROOT_PASSWORD=secret -e MYSQL_DATABASE=taskdb --name mysqldb -p 3307:3306 mysql:8.0
```

|Authentication Error|Exception thrown|HTTP Status code|
|--------------------|----------------|----------------|
|Bad login credentials|BadCredentialsException|401|
|Account locked|AccountStatusException|403|
|Not authorized to access a resource|AccessDeniedException|403
|Invalid JWT| SignatureException|401|   
|JWT has expired|ExpiredJwtException|401|  