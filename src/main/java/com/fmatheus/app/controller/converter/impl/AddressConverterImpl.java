package com.fmatheus.app.controller.converter.impl;

import com.fmatheus.app.controller.constant.FormatConstant;
import com.fmatheus.app.controller.converter.AddressConverter;
import com.fmatheus.app.controller.dto.request.AddressDtoRequest;
import com.fmatheus.app.controller.dto.response.AddressDtoResponse;
import com.fmatheus.app.controller.util.CharacterUtil;
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
        request.setPlace(CharacterUtil.removeDuplicateSpace(CharacterUtil.convertAllUppercaseCharacters(request.getPlace())));
        request.setComplement(Objects.nonNull(request.getComplement()) ? CharacterUtil.removeDuplicateSpace(CharacterUtil.convertAllUppercaseCharacters(request.getComplement())) : null);
        request.setDistrict(CharacterUtil.removeDuplicateSpace(CharacterUtil.convertAllUppercaseCharacters(request.getDistrict())));
        request.setCity(CharacterUtil.removeDuplicateSpace(CharacterUtil.convertAllUppercaseCharacters(request.getCity())));
        request.setState(CharacterUtil.convertAllUppercaseCharacters(request.getState()));
        request.setZipCode(CharacterUtil.removeSpecialCharacters(request.getZipCode()));
        return this.modelMapper.map(request, Address.class);
    }

    @Override
    public AddressDtoResponse converterToResponse(Address address) {
        address.setPlace(CharacterUtil.removeDuplicateSpace(CharacterUtil.convertFirstUppercaseCharacter(address.getPlace())));
        address.setComplement(Objects.nonNull(address.getComplement()) ? CharacterUtil.removeDuplicateSpace(CharacterUtil.convertFirstUppercaseCharacter(address.getComplement())) : null);
        address.setDistrict(CharacterUtil.removeDuplicateSpace(CharacterUtil.convertFirstUppercaseCharacter(address.getDistrict())));
        address.setCity(CharacterUtil.removeDuplicateSpace(CharacterUtil.convertFirstUppercaseCharacter(address.getCity())));
        address.setState(CharacterUtil.convertAllUppercaseCharacters(address.getState()));
        address.setZipCode(CharacterUtil.formatMask(address.getZipCode(), FormatConstant.ZIP_CODE));
        return this.modelMapper.map(address, AddressDtoResponse.class);
    }
}
