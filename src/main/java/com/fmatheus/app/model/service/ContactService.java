package com.fmatheus.app.model.service;

import com.fmatheus.app.model.entity.Contact;

import java.util.Optional;

public interface ContactService extends GenericService<Contact, Integer> {

    Optional<Contact> findByEmail(String email);

    Optional<Contact> findByPhone(String phone);

}
