package io.falcon.interview.virtualboard.services.domain.jpa.conversion;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import io.falcon.interview.virtualboard.services.domain.Post;
import io.falcon.interview.virtualboard.services.domain.jpa.PostEntity;

@Component
public class PostEntityToPostConverter implements Converter<PostEntity, Post> {

    @Override
    public Post convert(PostEntity entity) {
        Post post = new Post();
        post.setId(entity.getId());
        post.setIssued(entity.getIssued());
        post.setContent(entity.getContent());
        return post;
    }
}
