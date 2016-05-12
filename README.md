# falcon-inteview

The service runs as a Spring Boot application can be executed as a singe jar file.

How to run:
```
java -jar falcon-interview-1.0-SNAPSHOT.jar
```

The buniness channels can be configured by passing the following parameters as a VM options.

Configure JMS topics:
```
app.channels.posts.new={destination where the new posts to be saved will be sent to}
app.channels.posts.save.success={destination where succesfully saved posts will be sent to}
app.channels.posts.save.failed={destination where the result of the failed post saving will be sent to}
```

JMS and PostgreSQL configuration can be set according to the Spring Boot application conventions:

JPA: http://docs.spring.io/spring-boot/docs/current/reference/html/howto-data-access.html

JMS: http://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-messaging.html
