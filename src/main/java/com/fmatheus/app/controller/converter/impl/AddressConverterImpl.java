package com.fmatheus.app.controller.converter.impl;

import com.fmatheus.app.controller.constant.FormatConstant;
import com.fmatheus.app.controller.converter.AddressConverter;
import com.fmatheus.app.controller.dto.request.AddressDtoRequest;
import com.fmatheus.app.controller.dto.response.AddressDtoResponse;
import com.fmatheus.app.controller.util.AppUtil;
import com.fmatheus.app.model.entity.Address;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class AddressConverterImpl implements AddressConverter {

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public Address converterToRequest(AddressDtoRequest request) {
        request.setPlace(AppUtil.removeDuplicateSpace(AppUtil.convertAllUppercaseCharacters(request.getPlace())));
        request.setComplement(Objects.nonNull(request.getComplement()) ? AppUtil.removeDuplicateSpace(AppUtil.convertAllUppercaseCharacters(request.getComplement())) : null);
        request.setDistrict(AppUtil.removeDuplicateSpace(AppUtil.convertAllUppercaseCharacters(request.getDistrict())));
        request.setCity(AppUtil.removeDuplicateSpace(AppUtil.convertAllUppercaseCharacters(request.getCity())));
        request.setState(AppUtil.convertAllUppercaseCharacters(request.getState()));
        request.setZipCode(AppUtil.removeSpecialCharacters(request.getZipCode()));
        return this.modelMapper.map(request, Address.class);
    }

    @Override
    public AddressDtoResponse converterToResponse(Address address) {
        address.setPlace(AppUtil.removeDuplicateSpace(AppUtil.convertFirstUppercaseCharacter(address.getPlace())));
        address.setComplement(Objects.nonNull(address.getComplement()) ? AppUtil.removeDuplicateSpace(AppUtil.convertFirstUppercaseCharacter(address.getComplement())) : null);
        address.setDistrict(AppUtil.removeDuplicateSpace(AppUtil.convertFirstUppercaseCharacter(address.getDistrict())));
        address.setCity(AppUtil.removeDuplicateSpace(AppUtil.convertFirstUppercaseCharacter(address.getCity())));
        address.setState(AppUtil.convertAllUppercaseCharacters(address.getState()));
        address.setZipCode(AppUtil.formatMask(address.getZipCode(), FormatConstant.ZIP_CODE));
        return this.modelMapper.map(address, AddressDtoResponse.class);
    }
}
