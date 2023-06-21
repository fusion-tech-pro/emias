package com.fusiontech.emias.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class RouteException extends RuntimeException {
    public RouteException() {
        super();
    }

    public RouteException(String message, Throwable cause) {
        super(message, cause);
    }

    public RouteException(String message) {
        super(message);
    }

    public RouteException(Throwable cause) {
        super(cause);
    }
}
