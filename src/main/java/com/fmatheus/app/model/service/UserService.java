package com.fmatheus.app.model.service;

import com.fmatheus.app.model.entity.User;

import java.util.Optional;

public interface UserService extends GenericService<User, Integer> {

    Optional<User> findByUsername(String username);

}
