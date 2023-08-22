package com.fmatheus.app.controller.exception.swagger;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseAttribute {

    private String error;
    private String errorDescription;

}
