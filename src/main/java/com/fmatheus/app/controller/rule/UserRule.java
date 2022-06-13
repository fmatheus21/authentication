package com.fmatheus.app.controller.rule;

import com.fmatheus.app.controller.converter.UserConverter;
import com.fmatheus.app.controller.dto.response.UserDtoResponse;
import com.fmatheus.app.model.repository.filter.RepositoryFilter;
import com.fmatheus.app.model.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class UserRule {

    @Autowired
    private UserService userService;

    @Autowired
    private UserConverter userConverter;

    /**
     * Listar registros
     *
     * @return ResponseEntity<Page < UserDtoResponse>>
     * @author Fernando Matheus
     */
    public ResponseEntity<Page<UserDtoResponse>> findAll(Pageable pageable, RepositoryFilter filter) {
        var users = userService.findAllFilter(pageable, filter);
        return Objects.nonNull(users) ? ResponseEntity.ok
                (users.map(map -> this.userConverter.converterToResponse(map))) : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
