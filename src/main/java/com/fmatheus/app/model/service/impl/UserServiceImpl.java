package com.fmatheus.app.model.service.impl;

import com.fmatheus.app.model.entity.User;
import com.fmatheus.app.model.repository.UserRepository;
import com.fmatheus.app.model.repository.filter.RepositoryFilter;
import com.fmatheus.app.model.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Override
    public List<User> findAll() {
        return this.repository.findAll();
    }

    @Override
    public Optional<User> findById(Integer id) {
        return this.repository.findById(id);
    }

    @Override
    public <S extends User> S save(S s) {
        return this.repository.save(s);
    }

    @Override
    public void delete(User user) {
        this.repository.delete(user);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return this.repository.findByUsername(username);
    }

    public Page<User> findAllFilter(Pageable pageable, RepositoryFilter filter) {
        return repository.findAllFilter(pageable, filter);
    }
}
