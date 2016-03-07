package ru.piom.notes.app;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Alex on 3/7/2016.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String userId) {
        super("could not find user '" + userId + "'.");
    }
}
