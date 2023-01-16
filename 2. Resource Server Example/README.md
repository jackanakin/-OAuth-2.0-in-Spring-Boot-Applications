### Reference Documentation
* [Spring Web](https://docs.spring.io/spring-boot/docs/3.0.0/reference/htmlsingle/#web)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/docs/3.0.0/reference/htmlsingle/#using.devtools)
* [OAuth2 Resource Server](https://docs.spring.io/spring-boot/docs/3.0.0/reference/htmlsingle/#web.security.oauth2.server)

### In application.properties
```
spring.security.oauth2.resourceserver.jwt.jwk-set-uri=http://localhost:8080/realms/app/protocol/openid-connect/certs
```

### Testing Resource Server
1. Obtain an access_token using any of the methods in Chapter 1. Realm, Client, Token and Code Exchange
2. GET Request
```
var myHeaders = new Headers();
myHeaders.append("Authorization", "Bearer here_goes_access_token");

var requestOptions = {
  method: 'GET',
  headers: myHeaders,
  redirect: 'follow'
};

fetch("http://localhost:8081/users/status/check", requestOptions)
  .then(response => response.text())
  .then(result => console.log(result))
  .catch(error => console.log('error', error));
```