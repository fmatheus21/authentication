package com.fmatheus.app.controller.converter.impl;

import com.fmatheus.app.controller.constant.FormatConstant;
import com.fmatheus.app.controller.converter.ContactConverter;
import com.fmatheus.app.controller.dto.request.ContactDtoRequest;
import com.fmatheus.app.controller.dto.response.ContactDtoResponse;
import com.fmatheus.app.model.entity.Contact;
import com.fmatheus.app.controller.util.CharacterUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ContactConverterImpl implements ContactConverter {

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public Contact converterToRequest(ContactDtoRequest request) {
        request.setPhone(CharacterUtil.removeSpecialCharacters(request.getPhone()));
        request.setEmail(CharacterUtil.convertAllUppercaseCharacters(request.getEmail()));
        return this.modelMapper.map(request, Contact.class);
    }

    @Override
    public ContactDtoResponse converterToResponse(Contact contact) {
        contact.setPhone(CharacterUtil.formatMask(contact.getPhone(), FormatConstant.PHONE));
        contact.setEmail(CharacterUtil.convertAllLowercaseCharacters(contact.getEmail()));
        return this.modelMapper.map(contact, ContactDtoResponse.class);
    }
}
