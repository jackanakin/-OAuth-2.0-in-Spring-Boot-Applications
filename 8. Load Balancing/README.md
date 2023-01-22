## Load Balancing + API Gateway + Eureka Discovery + Resource Server

### API Gateway
1. pom.xml: Add "spring-cloud-starter-netflix-eureka-client"
2. ```application.properties: ``` 

    ```
    eureka.client.serviceUrl.defaultZone = http://localhost:8010/eureka
    spring.cloud.gateway.routes[x].uri = lb://my-resource-server
    ```
    
3. Add ```@EnableDiscoveryClient```

### Eureka Discovery
1. ```pom.xml```: Add ```spring-cloud-starter-netflix-eureka-client``` and ```spring-cloud-starter-netflix-eureka-server```

2. ```application.properties```:

    ```
    eureka.client.registerWithEureka=false
    eureka.client.fetchRegistry=false

    logging.level.com.netflix.eureka=OFF
    logging.level.com.netflix.discovery=OFF
    ```
    
3. Add ```@EnableEurekaServer```

### Resource Server
1. ```pom.xml```: Add ```spring-cloud-starter-netflix-eureka-client```
2. ```application.properties```: 
    ```
    eureka.client.serviceUrl.defaultZone = http://localhost:8010/eureka
    (optional)eureka.instance.instance-id=${spring.application.name}:${instanceId:${random.value}}
    ```
3. Add ```@EnableDiscoveryClient```
