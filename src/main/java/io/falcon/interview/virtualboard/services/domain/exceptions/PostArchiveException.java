package io.falcon.interview.virtualboard.services.domain.exceptions;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(INTERNAL_SERVER_ERROR)
public class PostArchiveException extends RuntimeException {

    public PostArchiveException() {
    }

    public PostArchiveException(String message) {
        super(message);
    }
}
