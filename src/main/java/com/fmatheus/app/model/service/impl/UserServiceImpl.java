package com.fmatheus.app.model.service.impl;

import com.fmatheus.app.controller.converter.UserConverter;
import com.fmatheus.app.controller.dto.request.UserDtoRequest;
import com.fmatheus.app.controller.dto.response.UserDtoResponse;
import com.fmatheus.app.model.entity.User;
import com.fmatheus.app.model.repository.PersonRepository;
import com.fmatheus.app.model.repository.UserRepository;
import com.fmatheus.app.model.repository.filter.RepositoryFilter;
import com.fmatheus.app.model.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.fmatheus.app.controller.util.AppUtil.removeSpecialCharacters;
import static java.util.Objects.nonNull;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private UserConverter userConverter;

    @Override
    public List<UserDtoResponse> findAll() {
        var list = this.repository.findAll();
        return this.converterList(list);
    }

    @Override
    public Optional<UserDtoResponse> findById(Integer id) {
        var result = this.repository.findById(id);
        return result.map(this::converter);
    }

    @Override
    public UserDtoResponse save(UserDtoResponse userDtoResponse) {
        return null;
    }

    @Override
    public UserDtoResponse save(UserDtoRequest dto) {
        var commit = this.personRepository.save(this.userConverter.converterToSave(dto));
        return this.converter(commit.getUser());
    }

    @Override
    public void deleteById(Integer id) {
        this.repository.deleteById(id);
    }

    @Override
    public Optional<UserDtoResponse> findByUsername(String username) {
        var result = this.repository.findByUsername(removeSpecialCharacters(username));
        return result.map(this::converter);
    }

    @Override
    public boolean checkUsernameExist(String username) {
        var user = this.repository.findByUsername(username).orElse(null);
        return nonNull(user);
    }

    public Page<UserDtoResponse> findAllFilter(Pageable pageable, RepositoryFilter filter) {
        var list = this.repository.findAllFilter(pageable, filter);
        var listConverter = list.map(this::converter).stream().toList();
        return new PageImpl<>(listConverter, pageable, this.totalPaginator(filter));
    }

    private Long totalPaginator(RepositoryFilter filter) {
        return this.repository.total(filter);
    }

    private UserDtoResponse converter(User user) {
        return this.userConverter.converterToResponse(user);
    }

    private List<UserDtoResponse> converterList(List<User> list) {
        return list.stream().map(map -> this.userConverter.converterToResponse(map)).toList();
    }

}
