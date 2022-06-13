package com.fmatheus.app.controller.enumerable;

import lombok.Getter;

public enum EntityEnum {

    ID("id"),
    NAME("name"),
    DOCUMENT("document"),
    EMAIL("email"),
    USERNAME("username"),
    ID_PERSON("idPerson"),;


    @Getter
    private final String value;

    EntityEnum(String value) {
        this.value = value;
    }

}
