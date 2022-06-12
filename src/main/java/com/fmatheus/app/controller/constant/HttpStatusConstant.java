package com.fmatheus.app.controller.constant;

public class HttpStatusConstant {

    private HttpStatusConstant() {
        throw new IllegalStateException(getClass().getName());
    }

    public static final String OK = "Ok";
    public static final String OK_NUMBER = "200";
    public static final String CREATED = "Created";
    public static final String CREATED_NUMBER = "201";
    public static final String FORBIDDEN = "Forbidden";
    public static final String FORBIDDEN_NUMBER = "403";
    public static final String INTERNAL_SERVER_ERROR = "Internal Server Error";
    public static final String INTERNAL_SERVER_ERROR_NUMBER = "500";
    public static final String NO_CONTENT = "No Content";
    public static final String NO_CONTENT_NUMBER = "204";
    public static final String UNAUTHORIZED = "Unauthorized";
    public static final String UNAUTHORIZED_NUMBER = "401";
    public static final String BAD_REQUEST = "Bad Request";
    public static final String BAD_REQUEST_NUMBER = "400";
    public static final String NOT_FOUND = "Not Found";
    public static final String NOT_FOUND_NUMBER = "404";

}