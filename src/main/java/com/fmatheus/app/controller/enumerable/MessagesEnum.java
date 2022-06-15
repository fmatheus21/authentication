package com.fmatheus.app.controller.enumerable;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Enum que representa as mensagens padrao da api que se encontrar em i18n/messages.yml
 *
 * @author Fernando Matheus
 */
public enum MessagesEnum {

    SUCCESS_CREATE(HttpStatus.CREATED, "message.success.create"),
    SUCCESS_UPDATE(HttpStatus.CREATED, "message.success.update"),
    SUCCESS_DELETE(HttpStatus.OK, "message.success.delete"),
    ERROR_INTERNAL(HttpStatus.INTERNAL_SERVER_ERROR, "message.error.internal-server"),
    ERROR_FORBIDDEN(HttpStatus.FORBIDDEN, "message.error.forbidden"),
    ERROR_BAD_REQUEST(HttpStatus.BAD_REQUEST, "message.error.bad-request"),
    ERROR_NOT_PERMISSION(HttpStatus.FORBIDDEN, "message.error.not-permission"),
    ERROR_NOT_FOUND(HttpStatus.BAD_REQUEST, "message.error.not-found"),
    ERROR_NOT_READABLE(HttpStatus.BAD_REQUEST, "message.error.not-readable"),
    ERROR_USER_NOTFOUND(HttpStatus.BAD_REQUEST, "message.error.user-notfound"),
    ERROR_USER_INACTIVE(HttpStatus.BAD_REQUEST, "message.error.user-inactive"),
    ERROR_NOT_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "message.error.unauthorized"),
    ERROR_RECORD_EXIST(HttpStatus.BAD_REQUEST, "message.error.record-exist"),
    ERROR_USERNAME_EXIST(HttpStatus.BAD_REQUEST, "message.error.username-exist"),
    ERROR_PHONE_EXIST(HttpStatus.BAD_REQUEST, "message.error.phone-exist"),
    ERROR_EMAIL_EXIST(HttpStatus.BAD_REQUEST, "message.error.email-exist");


    @Getter
    private final HttpStatus httpSttus;

    @Getter
    private final String message;

    MessagesEnum(HttpStatus httpSttus, String message) {
        this.httpSttus = httpSttus;
        this.message = message;
    }

}
