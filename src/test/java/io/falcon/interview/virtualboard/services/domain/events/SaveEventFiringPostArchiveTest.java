package io.falcon.interview.virtualboard.services.domain.events;

import static java.util.Arrays.asList;

import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jms.core.JmsMessagingTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.falcon.interview.virtualboard.services.domain.Post;
import io.falcon.interview.virtualboard.services.domain.PostArchive;
import io.falcon.interview.virtualboard.services.domain.exceptions.PostArchiveException;

public class SaveEventFiringPostArchiveTest {


    @Mock
    private PostArchive archive;
    @Mock
    private JmsMessagingTemplate messagingTemplate;
    @Mock
    private ObjectMapper json;
    @InjectMocks
    private SaveEventFiringPostArchive underTest;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        underTest.setSuccessChannel("success");
        underTest.setFailureChannel("failure");
    }

    @Test
    public void saveOnSuccess() throws Exception {
        Post toSave = newPost("comment");
        when(archive.save(toSave)).thenReturn(toSave);
        when(json.writeValueAsString(toSave)).thenReturn("postAsJson");

        Post saved = underTest.save(toSave);

        assertThat(saved, is(toSave));
        verify(messagingTemplate).convertAndSend("success", "postAsJson");
    }

    @Test
    public void saveOnFail() throws Exception {
        Post toSave = newPost("comment");
        PostArchiveException exception = new PostArchiveException("Exception message");
        when(archive.save(toSave)).thenThrow(exception);
        try {
            underTest.save(toSave);
            fail();
        } catch (PostArchiveException actualEx) {
            verify(messagingTemplate).convertAndSend("failure", "Exception message");
            assertThat(actualEx, is(exception));
        }
    }

    @Test
    public void get() {
        Post original = newPost("comment");
        when(archive.get("id")).thenReturn(original);

        Post actual = underTest.get("id");

        assertThat(actual, is(original));
    }

    @Test
    public void getAll() {
        Post original1 = newPost("comment1");
        Post original2 = newPost("comment2");
        when(archive.getAll()).thenReturn(asList(original1, original2));

        List<Post> allPosts = underTest.getAll();

        assertThat(allPosts, hasItems(original1, original2));
    }

    private Post newPost(String content) {
        Post post = new Post();
        post.setContent(content);
        return post;
    }
}
