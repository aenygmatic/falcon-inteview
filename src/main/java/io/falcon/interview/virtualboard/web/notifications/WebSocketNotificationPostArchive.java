package io.falcon.interview.virtualboard.web.notifications;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.core.convert.converter.Converter;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import io.falcon.interview.virtualboard.services.domain.Post;
import io.falcon.interview.virtualboard.services.domain.PostArchive;
import io.falcon.interview.virtualboard.web.model.PostModel;

@Primary
@Component
public class WebSocketNotificationPostArchive implements PostArchive {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    private PostArchive archive;
    @Autowired
    private Converter<Post, PostModel> postToModel;
    @Value("${channels.postsaved.name}")
    private String postSavedChannel;

    @Override
    public Post save(Post post) {
        Post saved = archive.save(post);
        messagingTemplate.convertAndSend(postSavedChannel, postToModel.convert(saved));
        return saved;
    }

    @Override
    public Post get(String id) {
        return archive.get(id);
    }

    @Override
    public List<Post> getAll() {
        return archive.getAll();
    }
}
