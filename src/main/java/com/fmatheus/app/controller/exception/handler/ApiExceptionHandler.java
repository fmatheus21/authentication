package com.fmatheus.app.controller.exception.handler;


import com.fmatheus.app.controller.enumerable.MessagesEnum;
import com.fmatheus.app.controller.exception.BadRequestException;
import com.fmatheus.app.controller.exception.ForbiddenException;
import com.fmatheus.app.controller.exception.UserInactiveException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Classe que captura as excecoes da api. A anotacao @ControllerAdvice significa
 * que esta classe ira monitorar todas as execoes da api.
 *
 * @author Fernando Matheus
 */
@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    private String message;

    private String cause;

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        var messageEnum = MessagesEnum.ERROR_NOT_READABLE;
        this.message = messageSource.getMessage(messageEnum.getMessage(), null, LocaleContextHolder.getLocale());
        this.cause = ex.getCause().toString();
        var error = this.erroMessageResponse(messageEnum, this.cause, this.message);
        return handleExceptionInternal(ex, error, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        var erros = this.createErros(ex.getBindingResult());
        return handleExceptionInternal(ex, erros, headers, status, request);
    }

    @ExceptionHandler({BadRequestException.class})
    public ResponseEntity<Object> handleBadRequestException(RuntimeException ex, WebRequest request) {
        this.message = this.messageSource.getMessage(ex.getMessage(), null, LocaleContextHolder.getLocale());
        this.cause = ExceptionUtils.getRootCauseMessage(ex);
        var erro = this.erroMessageResponse(MessagesEnum.ERROR_BAD_REQUEST, this.cause, this.message);
        return handleExceptionInternal(ex, erro, new HttpHeaders(), MessagesEnum.ERROR_BAD_REQUEST.getHttpSttus(), request);
    }

    @ExceptionHandler({DataIntegrityViolationException.class})
    public ResponseEntity<Object> handleDataIntegrityViolationException(RuntimeException ex, WebRequest request) {
        var messageEnum = MessagesEnum.ERROR_DATA_INTEGRITY_VIOLATION;
        this.message = this.messageSource.getMessage(ex.getMessage(), null, LocaleContextHolder.getLocale());
        this.cause = ExceptionUtils.getRootCauseMessage(ex);
        var erro = this.erroMessageResponse(messageEnum, this.cause, this.message);
        return handleExceptionInternal(ex, erro, new HttpHeaders(), messageEnum.getHttpSttus(), request);
    }

    @ExceptionHandler({ForbiddenException.class})
    public ResponseEntity<Object> handleForbiddenException(RuntimeException ex, WebRequest request) {
        var messageEnum = MessagesEnum.ERROR_NOT_PERMISSION;
        this.message = this.messageSource.getMessage(ex.getMessage(), null, LocaleContextHolder.getLocale());
        this.cause = ExceptionUtils.getRootCauseMessage(ex);
        var error = this.erroMessageResponse(messageEnum, this.cause, this.message);
        return handleExceptionInternal(ex, error, new HttpHeaders(), messageEnum.getHttpSttus(), request);
    }

    @ExceptionHandler({EmptyResultDataAccessException.class})
    public ResponseEntity<Object> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex, WebRequest request) {
        var messageEnum = MessagesEnum.ERROR_NOT_FOUND;
        this.message = this.messageSource.getMessage(Objects.requireNonNull(ex.getMessage()), null, LocaleContextHolder.getLocale());
        this.cause = ExceptionUtils.getRootCauseMessage(ex);
        var error = this.erroMessageResponse(messageEnum, this.cause, this.message);
        return handleExceptionInternal(ex, error, new HttpHeaders(), messageEnum.getHttpSttus(), request);
    }

    @ExceptionHandler({UserInactiveException.class})
    public ResponseEntity<Object> handleUserInactiveException(RuntimeException ex, WebRequest request) {
        var messageEnum = MessagesEnum.ERROR_NOT_UNAUTHORIZED;
        this.message = this.messageSource.getMessage(ex.getMessage(), null, LocaleContextHolder.getLocale());
        this.cause = ExceptionUtils.getRootCauseMessage(ex);
        var error = this.erroMessageResponse(messageEnum, this.cause, this.message);
        return handleExceptionInternal(ex, error, new HttpHeaders(), messageEnum.getHttpSttus(), request);
    }

    private MessageResponseHandler createErros(BindingResult result) {
        this.cause = "Argumentos Inválidos.";
        this.message = "Erro de validação dos campos.";
        var messageResponse = this.erroMessageResponse(MessagesEnum.ERROR_BAD_REQUEST, cause, message);
        List<FieldsResponseHandler> fields = new ArrayList<>();
        result.getFieldErrors().forEach(fieldError -> {
            this.message = this.messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
            var field = FieldsResponseHandler.builder()
                    .field(fieldError.getField())
                    .message(message)
                    .build();
            fields.add(field);
        });
        messageResponse.setFields(fields);
        return messageResponse;
    }


    private MessageResponseHandler erroMessageResponse(MessagesEnum messagesEnum, String cause, String message) {
        return new MessageResponseHandler(messagesEnum, cause, message);
    }


}