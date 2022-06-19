package com.fmatheus.app.controller.rule;

import com.fmatheus.app.controller.converter.UserConverter;
import com.fmatheus.app.controller.dto.request.UserDtoRequest;
import com.fmatheus.app.controller.dto.response.UserDtoResponse;
import com.fmatheus.app.controller.event.ResourceEvent;
import com.fmatheus.app.controller.exception.handler.response.MessageResponse;
import com.fmatheus.app.controller.util.AppUtil;
import com.fmatheus.app.model.repository.filter.RepositoryFilter;
import com.fmatheus.app.model.service.ContactService;
import com.fmatheus.app.model.service.PersonService;
import com.fmatheus.app.model.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@Component
public class UserRule {

    @Autowired
    private ApplicationEventPublisher publisher;

    @Autowired
    private PersonService personService;

    @Autowired
    private UserService userService;

    @Autowired
    private ContactService contactService;

    @Autowired
    private UserConverter userConverter;

    @Autowired
    private MessageResponseRule messageResponseRule;

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

    /**
     * Consultar registro por Id.
     *
     * @param id Id a ser consultado
     * @return ResponseEntity<UserDtoResponse>
     * @author Fernando Matheus
     */
    public ResponseEntity<UserDtoResponse> findById(int id) {
        var user = userService.findById(id).orElseThrow(
                () -> this.messageResponseRule.errorNotFound()
        );
        return ResponseEntity.status(HttpStatus.OK).body(this.userConverter.converterToResponse(user));
    }


    /**
     * Criar novo registro de usuario.
     *
     * @param request  UserDtoRequest
     * @param response HttpServletResponse
     * @return ResponseEntity<MessageResponse>
     * @author Fernando Matheus
     */
    public ResponseEntity<MessageResponse> create(UserDtoRequest request, HttpServletResponse response) {

        if (this.userService.checkUsernameExist(AppUtil.removeAllSpaces(request.getUsername()))) {
            throw this.messageResponseRule.badRequestErrorUsernameExist();
        }


        if (this.contactService.checkPhoneExist(AppUtil.removeSpecialCharacters(request.getContact().getPhone()))) {
            throw this.messageResponseRule.badRequestErrorPhoneExist();
        }

        if (this.contactService.checkEmailExist(request.getContact().getEmail())) {
            throw this.messageResponseRule.badRequestErrorEmailExist();
        }

        var person = this.userConverter.converterToSave(request);

        var commit = this.personService.save(person);

        this.publisher.publishEvent(new ResourceEvent(this, response, commit.getId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(this.messageResponseRule.messageSuccessCreate());

    }
}
