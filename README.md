# SpringBoot-blog
Simple Blog based on spring boot

* Using thymeleaf as template engine
* Using Spring Data JPA、Hibernate、MySQL enabling data persistence

### START
Recommended IntelliJ IDEA
open file `.\src\main\java\com\bzw875\blog\Application.java` click run button





### Deploying applications
+ create file `.\src\main\resources\application-prod.properties` 
+ build the skip test jar `mvn clean package -Dmaven.test.skip=true`
+ start java app `nohup java -jar xxx.jar --spring.profiles.active=prod &`

``` # application-prod.properties 文件
server.port=80
logging.level.root=info

spring.jpa.hibernate.ddl-auto=update
spring.datasource.url=jdbc:mysql://localhost:3306/blog?serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=123456

# blog admin user
system.user.name=username
system.user.password=password
``` 

```
// build
mvn clean package  -Dmaven.test.skip=true

// linux find java process
ps -ef | grep java

// linux kill java process
kill -9 PID


// run
java -jar app.jar
// nohup run
nohup java -jar app.jar &
// jvm params
java -Xms10m -Xmx80m -jar app.jar
// run using profiles
java -jar app.jar --spring.profiles.active=prod
// run specified port
java -jar app.jar --server.port=9090

// nohup run jar
nohup java -jar app.jar --spring.profiles.active=prod &

```

## login management
open url
http://localhost:8080/login
type blog admin user

## FEATURES
+ login security TODO
+ soft delete DONE
+ post reading statistics DONE
+ visitor statistics TODO
+ images update
+ code highlighting