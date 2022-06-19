package com.fmatheus.app.model.service;

import com.fmatheus.app.model.entity.Contact;

public interface ContactService extends GenericService<Contact, Integer> {

    boolean checkPhoneExist(String phone);

    boolean checkEmailExist(String email);

}
