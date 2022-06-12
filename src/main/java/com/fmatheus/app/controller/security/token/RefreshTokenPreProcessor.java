package com.fmatheus.app.controller.security.token;

import com.fmatheus.app.controller.constant.PropertiesConstant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RefreshTokenPreProcessor implements Filter {

    @Value(PropertiesConstant.SERVLET_CONTEXT_PATH)
    private String contextPath;

    @Value(PropertiesConstant.CONTEXT_PATH_TOKEN)
    private String contextPathToken;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        var req = (HttpServletRequest) request;

        var path = this.contextPath + this.contextPathToken;

        if (path.equalsIgnoreCase(req.getRequestURI())
                && "refresh_token".equals(req.getParameter("grant_type"))
                && req.getCookies() != null) {

            for (Cookie cookie : req.getCookies()) {
                if (cookie.getName().equals("refreshToken")) {
                    String refreshToken = cookie.getValue();
                    req = new MyServletRequestWrapper(req, refreshToken);
                }
            }

        }

        chain.doFilter(req, response);

    }

}
