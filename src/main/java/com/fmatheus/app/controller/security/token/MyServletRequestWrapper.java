package com.fmatheus.app.controller.security.token;

import org.apache.catalina.util.ParameterMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.Map;

public class MyServletRequestWrapper extends HttpServletRequestWrapper {

    private final String refreshToken;

    public MyServletRequestWrapper(HttpServletRequest request, String refreshToken) {
        super(request);
        this.refreshToken = refreshToken;
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        @SuppressWarnings({ "rawtypes", "unchecked" })
		ParameterMap<String, String[]> map = new ParameterMap(getRequest().getParameterMap());
        map.put("refresh_token", new String[]{refreshToken});
        map.setLocked(true);
        return map;
    }

}
