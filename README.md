# falcon-inteview

The service runs as a Spring Boot application can be executed as a singe jar file.

## How to run:
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

## Design

#####  Web module `io.falcon.interview.virtualboard.web`
Depends on: *Domain, Post feed producer*

This package is responsible for all the web based communication and the conversion between the json models sent and received by the UI and the core domain data models.


#####  Post feed module `io.falcon.interview.virtualboard.services.producer`
Depends on:  *n/a*

It's porpuse to submit the incoming post content to be saved asynchronously. It contains a jsm package which is the Spring JMS implementation of the interface which send the post content with a timestamp to the `app.channels.posts.new` channel.


#####  Post feed consumer module `io.falcon.interview.virtualboard.services.consumer`
Depends on:  *Domain*

The consumer processed the messages produced by the Post Feed from the `app.channels.posts.new` channel. It takes the JMS message and converts it the the domain data format.


#####  Domain module `io.falcon.interview.virtualboard.services.domain`
Depends on:  *n/a*

It contain all the domain interfaces, data objects and exceptions.


#####  Domain JPA module `io.falcon.interview.virtualboard.services.domain.jpa`
Depends on:  *Domain*

It is the Spring Data JPA based implementation of the domain logic.


#####  Domain events module `io.falcon.interview.virtualboard.services.domain.events`
Depends on:  *Domain*

This is a decorator over the domain logic. Since saving the post is async events should be generated to notify the clients on succesfull saving and handle the error on failure. In the case of successfull saving the module will convert the post to a JSON and send of to the `app.channels.posts.save.success` channel otherwise it sends the exception message to the `app.channels.posts.save.failed` channel. With this approach the clients see only the valid ans sucessfully saved post, so we can avoid that a post apeears on the clients but disappear on refresh due to some validation or persostance error.

Currently on the succes event the web module will consume the message and notifies the UI via websocket and the error event is just simply logged.

