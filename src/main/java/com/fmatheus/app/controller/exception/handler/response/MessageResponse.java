package com.fmatheus.app.controller.exception.handler.response;

import com.fmatheus.app.controller.enumerable.MessagesEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MessageResponse {

    private LocalDateTime timestamp = LocalDateTime.now();
    private int statusCode;
    private String statusDescription;
    private String cause;
    private String message;

    public MessageResponse(MessagesEnum messagesEnum, String cause, String message) {
        this.statusCode = messagesEnum.getHttpSttus().value();
        this.statusDescription = messagesEnum.getHttpSttus().name();
        this.cause = cause;
        this.message = message;
    }

}