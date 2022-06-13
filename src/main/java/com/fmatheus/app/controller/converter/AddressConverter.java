package com.fmatheus.app.controller.converter;

import com.fmatheus.app.controller.dto.request.AddressDtoRequest;
import com.fmatheus.app.controller.dto.response.AddressDtoResponse;
import com.fmatheus.app.model.entity.Address;

public interface AddressConverter extends MapperConverter<Address, AddressDtoRequest, AddressDtoResponse> {
}
