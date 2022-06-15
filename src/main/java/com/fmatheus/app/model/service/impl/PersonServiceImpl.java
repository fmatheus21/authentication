package com.fmatheus.app.model.service.impl;

import com.fmatheus.app.model.entity.Person;
import com.fmatheus.app.model.repository.PersonRepository;
import com.fmatheus.app.model.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonRepository repository;

    @Override
    public List<Person> findAll() {
        return this.repository.findAll();
    }

    @Override
    public Optional<Person> findById(Integer id) {
        return this.repository.findById(id);
    }

    @Override
    public <S extends Person> S save(S s) {
        return this.repository.save(s);
    }

    @Override
    public void delete(Person user) {
        this.repository.delete(user);
    }


}
