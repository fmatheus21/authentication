package com.fmatheus.app.model.service;

import com.fmatheus.app.controller.dto.request.UserDtoRequest;
import com.fmatheus.app.controller.dto.response.UserDtoResponse;
import com.fmatheus.app.model.repository.filter.RepositoryFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserService extends GenericService<UserDtoResponse, Integer> {

    UserDtoResponse save(UserDtoRequest dto);

    Optional<UserDtoResponse> findByUsername(String username);

    boolean checkUsernameExist(String username);

    Page<UserDtoResponse> findAllFilter(Pageable pageable, RepositoryFilter filter);

}
