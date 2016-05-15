package io.falcon.interview.virtualboard;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.ConfigFileApplicationContextInitializer;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import io.falcon.interview.virtualboard.web.VirtualBoardController;
import io.falcon.interview.virtualboard.web.model.NewPostRequest;
import io.falcon.interview.virtualboard.web.model.PostModel;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = VirtualBoardApplication.class, initializers = ConfigFileApplicationContextInitializer.class)
@IntegrationTest
public class VirtualBoardIntegrationTest {

    @Autowired
    private VirtualBoardController virtualBoardController;

    @Test
    public void testNewPostUseCase() {
        NewPostRequest request = new NewPostRequest();
        request.setContent("Content");
        virtualBoardController.addNewPost(request);

        waitForJmsExchange(50);

        List<PostModel> posts = virtualBoardController.getAllPosts();
        assertThat(posts.get(0).getContent(), is("Content"));
    }

    private void waitForJmsExchange(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
