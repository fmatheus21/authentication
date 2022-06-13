package com.fmatheus.app.controller.converter;

import com.fmatheus.app.controller.dto.request.PersonDtoRequest;
import com.fmatheus.app.controller.dto.response.PersonDtoResponse;
import com.fmatheus.app.model.entity.Person;

public interface PersonConverter extends MapperConverter<Person, PersonDtoRequest, PersonDtoResponse> {
}
