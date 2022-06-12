package com.fmatheus.app.controller.exception.handler;

import com.fmatheus.app.controller.enumerable.MessagesEnum;
import com.fmatheus.app.controller.exception.*;
import com.fmatheus.app.controller.exception.handler.response.MessageResponse;
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
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Classe que captura as excecoes da api. A anotacao @ControllerAdvice significa
 * que esta classe ira monitorar todas as execoes da api.
 *
 * @author Fernando Matheus
 */
@ControllerAdvice
public class AppExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    private String message;

    private String cause;

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        this.message = messageSource.getMessage(MessagesEnum.ERROR_NOT_READABLE.getMessage(), null, LocaleContextHolder.getLocale());
        this.cause = ex.getCause().toString();
        List<MessageResponse> erros = this.errosMessageResponse(MessagesEnum.ERROR_NOT_READABLE, this.cause, this.message);
        return handleExceptionInternal(ex, erros, headers, MessagesEnum.ERROR_NOT_READABLE.getHttpSttus(), request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<MessageResponse> erros = this.createListErros(ex.getBindingResult());
        return handleExceptionInternal(ex, erros, headers, MessagesEnum.ERROR_BAD_REQUEST.getHttpSttus(), request);
    }

    @ExceptionHandler({BadRequestException.class})
    public ResponseEntity<Object> handleBadRequestException(RuntimeException ex, WebRequest request) {
        this.message = this.messageSource.getMessage(ex.getMessage(), null, LocaleContextHolder.getLocale());
        this.cause = ExceptionUtils.getRootCauseMessage(ex);
        List<MessageResponse> erros = this.errosMessageResponse(MessagesEnum.ERROR_BAD_REQUEST, this.cause, this.message);
        return handleExceptionInternal(ex, erros, new HttpHeaders(), MessagesEnum.ERROR_BAD_REQUEST.getHttpSttus(), request);
    }

    @ExceptionHandler({ForbiddenException.class})
    public ResponseEntity<Object> handleForbiddenException(RuntimeException ex, WebRequest request) {
        this.message = messageSource.getMessage(ex.getMessage(), null, LocaleContextHolder.getLocale());
        this.cause = ExceptionUtils.getRootCauseMessage(ex);
        List<MessageResponse> erros = this.errosMessageResponse(MessagesEnum.ERROR_NOT_PERMISSION, this.cause, this.message);
        return handleExceptionInternal(ex, erros, new HttpHeaders(), MessagesEnum.ERROR_NOT_PERMISSION.getHttpSttus(), request);
    }


    @ExceptionHandler(ResponseStatusException.class)
    ResponseEntity<RestResponseHandler> handleStatusException(ResponseStatusException ex, WebRequest request) {
        return RestResponseHandler.builder().exception(ex).path(request.getDescription(false).substring(4)).entity();
    }

    @ExceptionHandler({DataIntegrityViolationException.class})
    public ResponseEntity<Object> handleEmptyResultDataAccessException(DataIntegrityViolationException ex, WebRequest request) {
        this.message = messageSource.getMessage(MessagesEnum.ERROR_BAD_REQUEST.getMessage(), null,
                LocaleContextHolder.getLocale());
        this.cause = ExceptionUtils.getRootCauseMessage(ex);
        List<MessageResponse> erros = this.errosMessageResponse(MessagesEnum.ERROR_BAD_REQUEST, this.cause, this.message);
        return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({EmptyResultDataAccessException.class})
    public ResponseEntity<Object> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex,
                                                                       WebRequest request) {
        this.message = messageSource.getMessage(MessagesEnum.ERROR_NOT_FOUND.getMessage(), null,
                LocaleContextHolder.getLocale());
        this.cause = ExceptionUtils.getRootCauseMessage(ex);
        List<MessageResponse> erros = this.errosMessageResponse(MessagesEnum.ERROR_NOT_FOUND, this.cause, this.message);
        return handleExceptionInternal(ex, erros, new HttpHeaders(), MessagesEnum.ERROR_NOT_FOUND.getHttpSttus(), request);
    }

    @ExceptionHandler({UserInactiveException.class})
    public ResponseEntity<Object> handleUserInactiveException(RuntimeException ex, WebRequest request) {
        this.message = messageSource.getMessage(ex.getMessage(), null, LocaleContextHolder.getLocale());
        this.cause = ExceptionUtils.getRootCauseMessage(ex);
        List<MessageResponse> erros = this.errosMessageResponse(MessagesEnum.ERROR_NOT_UNAUTHORIZED, this.cause, this.message);
        return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    private List<MessageResponse> createListErros(BindingResult result) {
        List<MessageResponse> erros = new ArrayList<>();
        result.getFieldErrors().forEach(fieldError -> {
            this.message = this.messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
            this.cause = fieldError.toString();
            erros.add(new MessageResponse(MessagesEnum.ERROR_BAD_REQUEST, cause, message));
        });
        return erros;
    }

    private List<MessageResponse> errosMessageResponse(MessagesEnum messagesEnum, String cause, String message) {
        return Collections.singletonList(new MessageResponse(
                messagesEnum,
                cause,
                message
        ));
    }


}
