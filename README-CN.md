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

```
// 打包
mvn clean package  -Dmaven.test.skip=true

// linux 查找java进程
ps -ef | grep java

// linux 杀死 java进程
kill -9 进程ID


// 运行
java -jar app.jar
// nohup 运行
nohup java -jar app.jar &
// jvm参数
java -Xms10m -Xmx80m -jar app.jar
// 指定使用配置
java -jar app.jar --spring.profiles.active=prod
// 指定端口
java -jar app.jar --server.port=9090

// 后台使用生产配置运行 jar
nohup java -jar app.jar --spring.profiles.active=prod &

// 退出
exit
```

## FEATURES
+ 登录安全 TODO
+ 软删除 DONE
+ 文章阅读次数 DONE
+ 访客数据统计 TODO
+ 图片上传和展示
+ 代码高亮实现（在代码第一行加上双斜杠+编程语言比如：'// javascript'）