## MVC WebApp + Load Balancing + API Gateway + Eureka Discovery + Resource Server

### API Gateway
1. pom.xml: Add "spring-cloud-starter-netflix-eureka-client"
2. application.properties: 
    Add:    eureka.client.serviceUrl.defaultZone = http://localhost:8010/eureka
    Change: spring.cloud.gateway.routes[x].uri = lb://my-resource-server
3. Add @EnableDiscoveryClient

### Eureka Discovery
1. pom.xml: Add "spring-cloud-starter-netflix-eureka-client" and "spring-cloud-starter-netflix-eureka-server"
2. application.properties: 
    eureka.client.registerWithEureka=false
    eureka.client.fetchRegistry=false

    logging.level.com.netflix.eureka=OFF
    logging.level.com.netflix.discovery=OFF
3. Add @EnableEurekaServer

### Resource Server
1. pom.xml: Add "spring-cloud-starter-netflix-eureka-client"
2. application.properties: 
    Add:    eureka.client.serviceUrl.defaultZone = http://localhost:8010/eureka
    Opt:    eureka.instance.instance-id=${spring.application.name}:${instanceId:${random.value}}
3. Add @EnableDiscoveryClient

### MVC WebApp
1. application.properties:
    spring.security.oauth2.client.registration.mywebclient.client-id = photo-app-webclient
    spring.security.oauth2.client.registration.mywebclient.client-secret = longlongsecret
    spring.security.oauth2.client.registration.mywebclient.scope = openid, profile, roles
    spring.security.oauth2.client.registration.mywebclient.authorization-grant-type = authorization_code
    spring.security.oauth2.client.registration.mywebclient.redirect-uri = http://localhost:8081/login/oauth2/code/mywebclient

    spring.security.oauth2.client.provider.mywebclient.authorization-uri = http://localhost:8080/realms/app/protocol/openid-connect/auth
    spring.security.oauth2.client.provider.mywebclient.token-uri = http://localhost:8080/realms/app/protocol/openid-connect/token
    spring.security.oauth2.client.provider.mywebclient.jwk-set-uri=http://localhost:8080/realms/app/protocol/openid-connect/certs
    spring.security.oauth2.client.provider.mywebclient.user-info-uri = http://localhost:8080/realms/app/protocol/openid-connect/userinfo
    spring.security.oauth2.client.provider.mywebclient.user-name-attribute = preferred_username
