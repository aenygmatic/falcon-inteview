package io.falcon.interview.virtualboard.services.domain.events;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.falcon.interview.virtualboard.services.domain.Post;
import io.falcon.interview.virtualboard.services.domain.PostArchive;

@Primary
@Service
public class SaveEventFiringPostArchive implements PostArchive {

    @Value("${channels.posts.saved.success}")
    private String successChannel;
    @Value("${channels.posts.saved.failed}")
    private String failureChannel;

    @Autowired
    private PostArchive archive;
    @Autowired
    private JmsMessagingTemplate messagingTemplate;
    @Autowired
    private ObjectMapper json;

    @Override
    public Post save(Post post) {
        try {
            Post saved = archive.save(post);
            messagingTemplate.convertAndSend(successChannel, toJson(saved));
            return saved;
        } catch (Exception e) {
            messagingTemplate.convertAndSend(failureChannel, e.getMessage());
            throw e;
        }
    }

    private String toJson(Post saved) {
        try {
            return json.writeValueAsString(saved);
        } catch (JsonProcessingException ignored) {
            /* This should never happen Post is a serializable POJO */
            throw new IllegalArgumentException(ignored);
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

    void setSuccessChannel(String successChannel) {
        this.successChannel = successChannel;
    }

    void setFailureChannel(String failureChannel) {
        this.failureChannel = failureChannel;
    }
}
