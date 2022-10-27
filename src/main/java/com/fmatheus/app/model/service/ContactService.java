package com.fmatheus.app.model.service;

import com.fmatheus.app.controller.dto.request.ContactDtoRequest;
import com.fmatheus.app.controller.dto.response.ContactDtoResponse;

public interface ContactService extends GenericService<ContactDtoResponse, Integer> {

    ContactDtoResponse save(ContactDtoRequest dto);

    boolean checkPhoneExist(String phone);

    boolean checkEmailExist(String email);

}
