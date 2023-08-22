package com.fmatheus.app.controller.exception.message;


import com.fmatheus.app.controller.enumerable.MessagesEnum;
import com.fmatheus.app.controller.exception.BadRequestException;
import com.fmatheus.app.controller.exception.ForbiddenException;
import com.fmatheus.app.controller.exception.UnauthorizedException;
import com.fmatheus.app.controller.exception.handler.MessageResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class MessageResponse {
    @Autowired
    private MessageSource messageSource;


    public MessageResponseHandler messageResponse(MessagesEnum messagesEnum) {
        String message = this.messageSource.getMessage(messagesEnum.getMessage(), (Object[]) null, LocaleContextHolder.getLocale());
        return new MessageResponseHandler(messagesEnum, messagesEnum.getHttpSttus().getReasonPhrase(), message);
    }

    public MessageResponseHandler successUpdate() {
        return messageResponse(MessagesEnum.SUCCESS_UPDATE);
    }

    public MessageResponseHandler successCreate() {
        return messageResponse(MessagesEnum.SUCCESS_CREATE);
    }

    public MessageResponseHandler successDelete() {
        return messageResponse(MessagesEnum.SUCCESS_DELETE);
    }

    public MessageResponseHandler error(HttpStatus httpStatus, String message) {
        return new MessageResponseHandler(httpStatus, message);
    }

    public BadRequestException errorRecordExist() {
        return new BadRequestException(MessagesEnum.ERROR_RECORD_EXIST);
    }

    public BadRequestException errorNotFound() {
        return new BadRequestException(MessagesEnum.ERROR_NOT_FOUND);
    }

    public ForbiddenException errorNotPermission() {
        return new ForbiddenException(MessagesEnum.ERROR_NOT_PERMISSION.getMessage());
    }

    public InternalError errorInternal() {
        return new InternalError(MessagesEnum.ERROR_INTERNAL.getMessage());
    }

    public ForbiddenException errorForbidden() {
        return new ForbiddenException(MessagesEnum.ERROR_FORBIDDEN.getMessage());
    }

    public BadRequestException errorCambiumNotConverter() {
        return new BadRequestException(MessagesEnum.ERROR_CAMBIUM_NOT_CONVERTER);
    }

    public BadRequestException errorUsernameExist(){
        return new BadRequestException(MessagesEnum.ERROR_USERNAME_EXIST);
    }

    public BadRequestException errorPhoneExist(){
        return new BadRequestException(MessagesEnum.ERROR_PHONE_EXIST);
    }

    public BadRequestException errorEmailExist(){
        return new BadRequestException(MessagesEnum.ERROR_EMAIL_EXIST);
    }

    public UnauthorizedException errorUserInactive(){
        return new UnauthorizedException(MessagesEnum.ERROR_USER_INACTIVE.getMessage());
    }


}
