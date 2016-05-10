package io.falcon.interview.virtualboard.services.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import io.falcon.interview.virtualboard.services.domain.Post;
import io.falcon.interview.virtualboard.services.domain.PostArchive;

@Service
public class JmsPostConsumer {

    @Autowired
    private PostArchive archive;
    @Autowired
    private Converter<Message<String>, Post> messageToPost;

    @JmsListener(destination = "${channels.newpost.name}")
    public void receivePost(Message<String> message) {
        archive.save(messageToPost.convert(message));
    }
}
