package io.falcon.interview.virtualboard.web.conversion;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import io.falcon.interview.virtualboard.services.domain.Post;
import io.falcon.interview.virtualboard.web.model.PostModel;

@Component
public class PostToPostModelConverter implements Converter<Post, PostModel> {

    @Override
    public PostModel convert(Post post) {
        PostModel model = new PostModel();
        model.setContent(post.getContent());
        model.setId(post.getId());
        model.setIssued(post.getIssued().getTime());
        return model;
    }
}
