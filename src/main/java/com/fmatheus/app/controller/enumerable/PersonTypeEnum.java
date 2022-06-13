package com.fmatheus.app.controller.enumerable;

import lombok.Getter;

public enum PersonTypeEnum {

    PESSOA_FISICA("PESSOA FISICA"),
    PESSOA_JURIDICA("PESSOA JURIDICA");


    @Getter
    private final String value;

    PersonTypeEnum(String value) {
        this.value = value;
    }

}
