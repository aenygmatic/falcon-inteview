package io.falcon.interview.virtualboard.services.producer.jms;

import java.util.Collections;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;

import io.falcon.interview.virtualboard.services.producer.PostFeed;

@Service
public class JmsPostFeed implements PostFeed {

    @Autowired
    private JmsMessagingTemplate messagingTemplate;
    @Value("${channels.posts.new}")
    private String channel;

    @Override
    public void addPost(String content) {
        Map<String, Object> headers = Collections.singletonMap("issued", new Date().getTime());
        messagingTemplate.convertAndSend(channel, content, headers);
    }
}
