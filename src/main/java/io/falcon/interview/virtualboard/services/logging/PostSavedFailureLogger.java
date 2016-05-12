package io.falcon.interview.virtualboard.services.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class PostSavedFailureLogger {

    private static final Logger LOGGER = LoggerFactory.getLogger(PostSavedFailureLogger.class);

    @JmsListener(destination = "${channels.posts.saved.failed}")
    public void onError(String message) {
        LOGGER.error("Logging saving error event! Reason: " + message);
    }
}
