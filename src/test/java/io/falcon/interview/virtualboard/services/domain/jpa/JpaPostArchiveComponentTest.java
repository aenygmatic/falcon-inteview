package io.falcon.interview.virtualboard.services.domain.jpa;

import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import io.falcon.interview.virtualboard.services.domain.Post;
import io.falcon.interview.virtualboard.services.domain.PostArchive;
import io.falcon.interview.virtualboard.services.domain.exceptions.PostNotFoundException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = JpaPostArchiveComponentTest.Config.class)
public class JpaPostArchiveComponentTest {

    @Configuration
    @EnableJpaRepositories
    @EnableTransactionManagement
    @ComponentScan
    @EnableAutoConfiguration
    public static class Config {
    }

    @Autowired
    private PostArchive underTest;
    @Autowired
    private PostEntityCrudRepository repository;

    @After
    public void cleanUpDb() {
        repository.deleteAll();
    }

    @Test
    public void testSingleSaving() {
        Date now = new Date();
        Post toSave = newPost("Comment", now);

        Post saved = underTest.save(toSave);

        assertNotNull(saved.getId());
        assertThat(saved.getContent(), is("Comment"));
        assertThat(saved.getIssued().getTime(), is(now.getTime()));
    }

    @Test
    public void testSaveAndGet() {
        Date now = new Date();
        Post toSave = newPost("Comment", now);

        Post saved = underTest.save(toSave);
        Post received = underTest.get(saved.getId());

        assertThat(received.getId(), is(saved.getId()));
        assertThat(received.getContent(), is("Comment"));
        assertThat(received.getIssued().getTime(), is(now.getTime()));
    }

    @Test(expected = PostNotFoundException.class)
    public void testGetNotFoundNullId() {
        underTest.get(null);
    }

    @Test(expected = PostNotFoundException.class)
    public void testGetNotFoundNonExistingId() {
        underTest.get("invalid");
    }

    @Test
    public void testGetAllWithEmptyDb() {
        assertThat(underTest.getAll(), is(empty()));
    }

    @Test
    public void testGetAll() {
        long now = new Date().getTime();
        Post first = newPost("Comment1", new Date(now - 10));
        Post second = newPost("Comment2", new Date(now + 50));

        underTest.save(second);
        underTest.save(first);

        List<Post> posts = underTest.getAll();

        assertThat(posts.get(0).getContent(), is("Comment1"));
        assertThat(posts.get(0).getIssued().getTime(), is(now - 10));
        assertThat(posts.get(1).getContent(), is("Comment2"));
        assertThat(posts.get(1).getIssued().getTime(), is(now + 50));
    }

    private Post newPost(String comment, Date now) {
        Post toSave = new Post();
        toSave.setIssued(now);
        toSave.setContent(comment);
        return toSave;
    }

}