package io.falcon.interview.virtualboard.services.consumer;

import java.util.Date;

import org.springframework.core.convert.converter.Converter;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import io.falcon.interview.virtualboard.services.domain.Post;

@Component
public class JmsMessageToPostConverter implements Converter<Message<String>, Post> {

    @Override
    public Post convert(Message<String> message) {
        Post post = new Post();
        post.setContent(message.getPayload());
        post.setIssued(getIssuedDate(message));
        return post;
    }


    private Date getIssuedDate(Message<String> message) {
        return new Date(message.getHeaders().get("issued", Long.class));
    }

}
