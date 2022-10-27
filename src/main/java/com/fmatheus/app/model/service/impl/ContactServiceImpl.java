package com.fmatheus.app.model.service.impl;

import com.fmatheus.app.controller.converter.ContactConverter;
import com.fmatheus.app.controller.dto.request.ContactDtoRequest;
import com.fmatheus.app.controller.dto.response.ContactDtoResponse;
import com.fmatheus.app.model.entity.Contact;
import com.fmatheus.app.model.repository.ContactRepository;
import com.fmatheus.app.model.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    private ContactRepository repository;

    @Autowired
    private ContactConverter contactConverter;

    @Override
    public List<ContactDtoResponse> findAll() {
        var list = this.repository.findAll();
        return this.converterList(list);
    }

    @Override
    public Optional<ContactDtoResponse> findById(Integer id) {
        var result = this.repository.findById(id);
        return result.map(this::converter);
    }

    @Override
    public ContactDtoResponse save(ContactDtoResponse contactDtoResponse) {
        return null;
    }

    @Override
    public ContactDtoResponse save(ContactDtoRequest dto) {
        return null;
    }

    @Override
    public void deleteById(Integer id) {
        this.repository.deleteById(id);
    }

    @Override
    public boolean checkPhoneExist(String phone) {
        return repository.findByPhone(phone);
    }

    @Override
    public boolean checkEmailExist(String email) {
        return repository.findByEmail(email);
    }

    private ContactDtoResponse converter(Contact contact) {
        return this.contactConverter.converterToResponse(contact);
    }

    private List<ContactDtoResponse> converterList(List<Contact> list) {
        return list.stream().map(map -> this.contactConverter.converterToResponse(map)).toList();
    }


}
