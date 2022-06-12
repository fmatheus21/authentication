package com.fmatheus.app.controller.exception.handler;

import lombok.Data;

import java.time.LocalDateTime;


@Data
public class RestResponseHandler {

    private LocalDateTime timestamp = LocalDateTime.now();
    private int status;
    private String error;
    private String message;
    private String path;

    public RestResponseHandler() {
    }

    public RestResponseHandler(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public static RestResponseBuilderHandler builder() {
        return new RestResponseBuilderHandler();
    }

}
