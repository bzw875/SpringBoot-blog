# SpringBoot-blog
基于Spring Boot 的简单博客

* 使用 thymeleaf 做模版引擎
* 使用Spring Data JPA和Hibernate来连接、处理MySQL数据库
* 设置了Intellij IDEA和spring-boot-devtools依赖实现了热部署 [SpringBoot项目在IntelliJ IDEA中实现热部署](https://blog.csdn.net/My_Chen_Suo_Zhang/article/details/69396808)

### 起步
导入Intellij 执行 `.\src\main\java\com\bzw875\blog\Application.java`的main方法就是启动了





### 发包
+ 新增文件 `.\src\main\resources\application-prod.properties` 
+ 项目目录`mvn clean package`打jar包
+ 执行`java -jar xxx.jar --spring.profiles.active=prod`

``` # application-prod.properties 文件
server.port=80
logging.level.root=info

spring.jpa.hibernate.ddl-auto=update
spring.datasource.url=jdbc:mysql://localhost:3306/blog?serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=123456


system.user.name=username
system.user.password=password
``` 
