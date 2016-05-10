package io.falcon.interview.virtualboard.web.notifications;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsMessagingTemplate;

import io.falcon.interview.virtualboard.services.domain.Post;
import io.falcon.interview.virtualboard.services.domain.PostArchive;
import io.falcon.interview.virtualboard.services.domain.exceptions.PostArchiveException;

public class EventFiringPostArchive implements PostArchive {

    @Autowired
    private JmsMessagingTemplate messagingTemplate;
    @Value("${channels.postsaved.name}")
    private String channel;

    @Autowired
    private PostArchive archive;

    @Override
    public Post save(Post post) {
        try {
            Post saved = archive.save(post);
            return saved;
        } catch (PostArchiveException e) {
            throw e;
        }
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
