package io.falcon.interview.virtualboard.services.domain.jpa.conversion;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import io.falcon.interview.virtualboard.services.domain.Post;
import io.falcon.interview.virtualboard.services.domain.jpa.PostEntity;

@Component
public class PostToPostEntityConverter implements Converter<Post, PostEntity> {


    @Override
    public PostEntity convert(Post post) {
        PostEntity entity = new PostEntity();
        entity.setContent(post.getContent());
        entity.setIssued(post.getIssued());
        return entity;
    }
}
