package com.fmatheus.app.controller.enumerable;

import lombok.Getter;

public enum EntityEnum {

    ID("id"),
    NAME("name"),
    DOCUMENT("document"),
    EMAIL("email"),
    PHONE("phone"),
    ID_PERSON("idPerson"),
    CONTACT("contactEntity"),
    ID_CLIENT("idClient"),
    PLATE("plate"),
    RENAVAM("renavam"),
    VEHICLE("vehicleEntity"),
    USERNAME("username"),
    ID_CONSULTANT("idConsultant"),
    ID_CONTRACT("idContract"),
    DATESIGNED("dateSigned"),
    PLAN("plan"),
    DATE_SIGNED("dateSigned"),
    DISTRICT("district"),
    CONSULTANT("consultant"),
    CITY("city"),
    BRAND("brand");


    @Getter
    private final String value;

    EntityEnum(String value) {
        this.value = value;
    }

}
