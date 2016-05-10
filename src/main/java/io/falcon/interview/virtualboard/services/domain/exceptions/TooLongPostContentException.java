package io.falcon.interview.virtualboard.services.domain.exceptions;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(BAD_REQUEST)
public class TooLongPostContentException extends PostArchiveException{
}
