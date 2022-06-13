package com.fmatheus.app.controller.converter;

import com.fmatheus.app.controller.dto.response.UserDtoResponse;
import com.fmatheus.app.controller.dto.request.UserDtoRequest;
import com.fmatheus.app.model.entity.User;

public interface UserConverter extends MapperConverter<User, UserDtoRequest, UserDtoResponse> {
}
