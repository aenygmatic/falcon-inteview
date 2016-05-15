package io.falcon.interview.virtualboard.services.domain.jpa;

import static java.util.stream.Collectors.toList;
import static java.util.stream.StreamSupport.stream;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.falcon.interview.virtualboard.services.domain.Post;
import io.falcon.interview.virtualboard.services.domain.PostArchive;
import io.falcon.interview.virtualboard.services.domain.exceptions.InvalidPostException;
import io.falcon.interview.virtualboard.services.domain.exceptions.PostNotFoundException;
import io.falcon.interview.virtualboard.services.domain.exceptions.TooLongPostContentException;

@Service
@Transactional
public class JpaPostArchive implements PostArchive {

    private static final Sort SORTED_BY_ISSUED = new Sort(Sort.Direction.ASC, "issued");

    @Autowired
    private Converter<Post, PostEntity> postToEntity;
    @Autowired
    private Converter<PostEntity, Post> entityToPost;
    @Autowired
    private PostEntityCrudRepository repository;

    @Override
    public Post save(Post post) {
        validatePost(post);
        PostEntity entity = convertWithId(post);
        PostEntity savedEntity = repository.save(entity);
        return entityToPost.convert(savedEntity);
    }

    private PostEntity convertWithId(Post post) {
        PostEntity entity = postToEntity.convert(post);
        entity.setId(UUID.randomUUID().toString());
        return entity;
    }

    private void validatePost(Post post) {
        assertNotNull(post);
        assertPostLength(post);
    }

    private void assertPostLength(Post post) {
        if (post.getContent().length() > 500) {
            throw new TooLongPostContentException("Length of the post cannot be longer than 500 character!");
        }
    }

    private void assertNotNull(Post post) {
        if (post == null || post.getContent() == null) {
            throw new InvalidPostException("Neither post or post comment cannot be null!");
        }
    }

    @Override
    public Post get(String id) {
        assertId(id);
        PostEntity entity = repository.findOne(id);
        if (entity != null) {
            return entityToPost.convert(entity);
        } else {
            throw new PostNotFoundException("No post found with id: " + id);
        }
    }

    @Override
    public List<Post> getAll() {
        return stream(repository.findAll(SORTED_BY_ISSUED).spliterator(), false)
                .map(entityToPost::convert)
                .collect(toList());
    }

    private void assertId(String id) {
        if (id == null) {
            throw new PostNotFoundException("No post found with null id!");
        }
    }


}
