package io.falcon.interview.virtualboard.web;

import static java.util.stream.Collectors.toList;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.falcon.interview.virtualboard.services.domain.Post;
import io.falcon.interview.virtualboard.services.domain.PostArchive;
import io.falcon.interview.virtualboard.services.producer.PostFeed;
import io.falcon.interview.virtualboard.web.model.NewPostRequest;
import io.falcon.interview.virtualboard.web.model.PostModel;

@RestController
@RequestMapping("/posts")
public class VirtualBoardController {

    @Autowired
    private PostFeed feed;
    @Autowired
    private PostArchive archive;
    @Autowired
    private Converter<Post, PostModel> postToModel;

    @RequestMapping(method = POST, path = "/")
    public void addNewPost(NewPostRequest request) {
        feed.addPost(request.getContent());
    }

    @RequestMapping(method = GET, path = "/{id}")
    public PostModel getPost(@PathVariable String id) {
        return postToModel.convert(archive.get(id));
    }

    @RequestMapping(method = GET, path = "/")
    public List<PostModel> getAllPosts() {
        return archive.getAll().stream()
                .map(postToModel::convert)
                .collect(toList());
    }
}
