package com.fmatheus.app.controller.constant;


public class PropertiesConstant {

    private PropertiesConstant() {
        throw new IllegalStateException("PropertiesConstant class");
    }

    public static final String ALLOW_ORIGIN = "${security.config.allow-origin}";
    public static final String WITH_CLIENT = "${security.config.with-client}";
    public static final String SECRET_CLIENT = "${security.config.secret-client}";
    public static final String JWT_SIGNING_KEY = "${security.config.jwt-signing-key}";
    public static final String SECURE_HTTPS = "${security.config.secure-https}";
    public static final String CONTEXT_PATH_TOKEN = "${security.config.context-path-token}";
    public static final String TOKEN_VALIDITY_SECONDS = "${security.config.token-validity-seconds}";
    public static final String REFRESH_TOKEN_VALIDITY_SECONDS = "${security.config.refresh-token-validity-seconds}";
    public static final String SERVLET_CONTEXT_PATH = "${server.servlet.context-path}";
    public static final String OPENAPI_DESCRIPTION = "${openapi.application.description}";
    public static final String OPENAPI_VERSION = "${openapi.application.version}";
    public static final String OPENAPI_TITLE = "${openapi.application.title}";
    public static final String OPENAPI_API_DOCS_PATH = "${openapi.application.api-docs-path}";
    public static final String OPENAPI_API_SWAGGER_PATH = "${openapi.application.swagger-ui-path}";
    public static final String RESOURCE_CLASSPATH_STATIC = "${resource.classpath.static}";

}
