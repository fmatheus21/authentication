package com.fmatheus.app.controller.exception.handler;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;


public class RestResponseBuilderHandler {

    private int status;
    private String error;
    private String message;
    private String path;

    public RestResponseBuilderHandler status(int status) {
        this.status = status;
        return this;
    }

    public RestResponseBuilderHandler status(HttpStatus status) {
        this.status = status.value();

        if (status.isError()) {
            this.error = status.getReasonPhrase();
        }

        return this;
    }

    public RestResponseBuilderHandler error(String error) {
        this.error = error;
        return this;
    }

    public RestResponseBuilderHandler exception(ResponseStatusException exception) {
        HttpStatus httpStatus = exception.getStatus();
        this.status = httpStatus.value();

        if (!Objects.requireNonNull(exception.getReason()).isBlank()) {
            this.message = exception.getReason();
        }

        if (httpStatus.isError()) {
            this.error = httpStatus.getReasonPhrase();
        }

        return this;
    }

    public RestResponseBuilderHandler message(String message) {
        this.message = message;
        return this;
    }

    public RestResponseBuilderHandler path(String path) {
        this.path = path;
        return this;
    }

    public RestResponseHandler build() {
        RestResponseHandler response = new RestResponseHandler();
        response.setStatus(status);
        response.setError(error);
        response.setMessage(message);
        response.setPath(path);
        return response;
    }

    public ResponseEntity<RestResponseHandler> entity() {
        return ResponseEntity.status(status).headers(HttpHeaders.EMPTY).body(build());
    }

}
