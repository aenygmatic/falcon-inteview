package io.falcon.interview.virtualboard.web.notifications;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.falcon.interview.virtualboard.services.domain.Post;
import io.falcon.interview.virtualboard.web.model.PostModel;

@Component
public class PostSavedEventWebNotifier {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    private Converter<Post, PostModel> postToModel;
    @Autowired
    private ObjectMapper json;

    @JmsListener(destination = "${channels.posts.saved.success}")
    public void onSave(String postAsJson) {
        Post post = toObject(postAsJson);
        messagingTemplate.convertAndSend("/post-stream/new-post", postToModel.convert(post));
    }

    private Post toObject(String postAsJson) {
        try {
            return json.readValue(postAsJson, Post.class);
        } catch (IOException e) {
            /* This should never happen Post is a serializable POJO */
            throw new IllegalArgumentException(e);
        }
    }
}
