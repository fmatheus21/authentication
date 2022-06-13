package com.fmatheus.app.controller.converter;

import com.fmatheus.app.controller.dto.request.ContactDtoRequest;
import com.fmatheus.app.controller.dto.response.ContactDtoResponse;
import com.fmatheus.app.model.entity.Contact;

public interface ContactConverter extends MapperConverter<Contact, ContactDtoRequest, ContactDtoResponse> {
}
