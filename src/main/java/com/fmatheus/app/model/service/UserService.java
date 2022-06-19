package com.fmatheus.app.model.service;

import com.fmatheus.app.model.entity.User;
import com.fmatheus.app.model.repository.filter.RepositoryFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserService extends GenericService<User, Integer> {

    Optional<User> findByUsername(String username);

    boolean checkUsernameExist(String username);

    Page<User> findAllFilter(Pageable pageable, RepositoryFilter filter);

}
