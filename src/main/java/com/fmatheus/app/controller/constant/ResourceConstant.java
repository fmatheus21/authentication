package com.fmatheus.app.controller.constant;

public class ResourceConstant {

    private ResourceConstant() {
        throw new IllegalStateException(getClass().getName());
    }

    public static final String ID_RESOURCE = "/{\\d+}";
    public static final String ID = "/{id}";
    public static final String USERS = "/users";

}
