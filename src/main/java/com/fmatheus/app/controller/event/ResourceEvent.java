package com.fmatheus.app.controller.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import javax.servlet.http.HttpServletResponse;
import java.io.Serial;

public class ResourceEvent extends ApplicationEvent {

    @Serial
    private static final long serialVersionUID = 1L;

    @Getter
    private final HttpServletResponse response;

    @Getter
    private final Integer id;

    public ResourceEvent(Object source, HttpServletResponse response, Integer id) {
        super(source);
        this.response = response;
        this.id = id;
    }

}
