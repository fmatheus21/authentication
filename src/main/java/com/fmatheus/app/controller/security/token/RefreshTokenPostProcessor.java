package com.fmatheus.app.controller.security.token;

import com.fmatheus.app.controller.constant.PropertiesConstant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@ControllerAdvice
public class RefreshTokenPostProcessor implements ResponseBodyAdvice<OAuth2AccessToken> {

    @Value(PropertiesConstant.SECURE_HTTPS)
    private boolean secureHttps;

    @Value(PropertiesConstant.CONTEXT_PATH_TOKEN)
    private String contextPathToken;

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return Objects.requireNonNull(returnType.getMethod()).getName().equalsIgnoreCase("postAccessToken");
    }

    @Override
    public OAuth2AccessToken beforeBodyWrite(OAuth2AccessToken body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {

        var req = ((ServletServerHttpRequest) request).getServletRequest();
        var res = ((ServletServerHttpResponse) response).getServletResponse();

        DefaultOAuth2AccessToken token = (DefaultOAuth2AccessToken) body;

        var refreshToken = Objects.requireNonNull(body).getRefreshToken().getValue();
        this.addRefreshTokenCookie(refreshToken, req, res);
        this.removeRefreshTokenBody(token);

        return body;

    }

    private void addRefreshTokenCookie(String refreshToken, HttpServletRequest req, HttpServletResponse res) {
        var cookie = new Cookie("refreshToken", refreshToken);
        cookie.setHttpOnly(true);
        cookie.setSecure(this.secureHttps);
        cookie.setPath(req.getContextPath() + this.contextPathToken);
        cookie.setMaxAge(2592000);
        res.addCookie(cookie);
    }

    private void removeRefreshTokenBody(DefaultOAuth2AccessToken token) {
        token.setRefreshToken(null);
    }

}
