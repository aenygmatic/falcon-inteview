package io.falcon.interview.virtualboard;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.ConfigFileApplicationContextInitializer;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = VirtualBoardApplication.class, initializers = ConfigFileApplicationContextInitializer.class)
@IntegrationTest
public class VirtualBoardIntegrationTest {

    @Test
    public void testStartup() {

    }
}
