package com.fmatheus.app.controller.converter.impl;

import com.fmatheus.app.controller.converter.AddressConverter;
import com.fmatheus.app.controller.converter.ContactConverter;
import com.fmatheus.app.controller.converter.PersonConverter;
import com.fmatheus.app.controller.converter.UserConverter;
import com.fmatheus.app.controller.dto.request.UserDtoRequest;
import com.fmatheus.app.controller.dto.response.UserDtoResponse;
import com.fmatheus.app.model.entity.Person;
import com.fmatheus.app.model.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserConverterImpl implements UserConverter {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PersonConverter personConverter;

    @Autowired
    private ContactConverter contactConverter;

    @Autowired
    private AddressConverter addressConverter;

    @Override
    public User converterToRequest(UserDtoRequest request) {
        return null;
    }

    @Override
    public UserDtoResponse converterToResponse(User user) {
        var personResponse = this.personConverter.converterToResponse(user.getIdPerson());
        var contactResponse = this.contactConverter.converterToResponse(user.getIdPerson().getContact());
        var addressResponse = this.addressConverter.converterToResponse(user.getIdPerson().getAddress());
        var response = this.modelMapper.map(user, UserDtoResponse.class);
        response.setPerson(personResponse);
        response.setContact(contactResponse);
        response.setAddress(addressResponse);
        return response;
    }

    @Override
    public Person converterToSave(UserDtoRequest request) {

        var person = this.personConverter.converterToRequest(request.getPerson());

        var address = this.addressConverter.converterToRequest(request.getAddress());
        address.setPerson(person);

        var contact = this.contactConverter.converterToRequest(request.getContact());
        contact.setPerson(person);

        person.setAddress(address);
        person.setContact(contact);

        return person;
    }
}
