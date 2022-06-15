package com.fmatheus.app.controller.converter.impl;

import com.fmatheus.app.controller.constant.FormatConstant;
import com.fmatheus.app.controller.converter.ContactConverter;
import com.fmatheus.app.controller.dto.request.ContactDtoRequest;
import com.fmatheus.app.controller.dto.response.ContactDtoResponse;
import com.fmatheus.app.controller.util.AppUtil;
import com.fmatheus.app.model.entity.Contact;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ContactConverterImpl implements ContactConverter {

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public Contact converterToRequest(ContactDtoRequest request) {
        request.setPhone(AppUtil.removeSpecialCharacters(request.getPhone()));
        request.setEmail(AppUtil.convertAllUppercaseCharacters(request.getEmail()));
        return this.modelMapper.map(request, Contact.class);
    }

    @Override
    public ContactDtoResponse converterToResponse(Contact contact) {
        contact.setPhone(AppUtil.formatMask(contact.getPhone(), FormatConstant.PHONE));
        contact.setEmail(AppUtil.convertAllLowercaseCharacters(contact.getEmail()));
        return this.modelMapper.map(contact, ContactDtoResponse.class);
    }
}
