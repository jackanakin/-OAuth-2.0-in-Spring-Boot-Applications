### Reference Documentation
* [Spring Web](https://docs.spring.io/spring-boot/docs/3.0.0/reference/htmlsingle/#web)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/docs/3.0.0/reference/htmlsingle/#using.devtools)
* [OAuth2 Resource Server](https://docs.spring.io/spring-boot/docs/3.0.0/reference/htmlsingle/#web.security.oauth2.server)

### In application.properties
```
spring.security.oauth2.resourceserver.jwt.jwk-set-uri=http://localhost:8080/realms/app/protocol/openid-connect/certs
```

### Resource Server: Role Based Access Control Example
