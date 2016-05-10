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
import io.falcon.interview.virtualboard.services.domain.exceptions.PostNotFoundException;

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
        PostEntity entity = postToEntity.convert(post);
        entity.setId(UUID.randomUUID().toString());
        PostEntity savedEntity = repository.save(entity);
        return entityToPost.convert(savedEntity);
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
