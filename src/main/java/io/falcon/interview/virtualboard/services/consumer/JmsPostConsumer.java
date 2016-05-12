package io.falcon.interview.virtualboard.services.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import io.falcon.interview.virtualboard.services.domain.Post;
import io.falcon.interview.virtualboard.services.domain.PostArchive;

@Service
public class JmsPostConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(JmsPostConsumer.class);

    @Autowired
    private PostArchive archive;
    @Autowired
    private Converter<Message<String>, Post> messageToPost;

    @JmsListener(destination = "${channels.posts.new}")
    public void receivePost(Message<String> message) {
        try {
            archive.save(messageToPost.convert(message));
        } catch (Exception e) {
            LOGGER.info("Failed to perform saving of post!");
        }
    }
}
