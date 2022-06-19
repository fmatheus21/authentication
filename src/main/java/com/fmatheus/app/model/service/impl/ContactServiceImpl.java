package com.fmatheus.app.model.service.impl;

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

    @Override
    public List<Contact> findAll() {
        return this.repository.findAll();
    }

    @Override
    public Optional<Contact> findById(Integer id) {
        return this.repository.findById(id);
    }

    @Override
    public <S extends Contact> S save(S s) {
        return this.repository.save(s);
    }

    @Override
    public void delete(Contact user) {
        this.repository.delete(user);
    }

    @Override
    public boolean checkPhoneExist(String phone) {
        return repository.findByPhone(phone);
    }

    @Override
    public boolean checkEmailExist(String email) {
        return repository.findByEmail(email);
    }


}
