package com.fmatheus.app.controller.rule;

import com.fmatheus.app.controller.converter.UserConverter;
import com.fmatheus.app.controller.dto.request.UserDtoRequest;
import com.fmatheus.app.controller.dto.response.UserDtoResponse;
import com.fmatheus.app.controller.event.ResourceEvent;
import com.fmatheus.app.controller.exception.handler.MessageResponseHandler;
import com.fmatheus.app.controller.exception.message.MessageResponse;
import com.fmatheus.app.controller.util.CharacterUtil;
import com.fmatheus.app.model.repository.filter.RepositoryFilter;
import com.fmatheus.app.model.service.ContactService;
import com.fmatheus.app.model.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;

@Component
public class UserRule {

    @Autowired
    private ApplicationEventPublisher publisher;

    @Autowired
    private UserService userService;

    @Autowired
    private ContactService contactService;

    @Autowired
    private UserConverter userConverter;

    @Autowired
    private MessageResponse messageResponse;

    /**
     * Listar registros
     *
     * @return Page<UserDtoResponse>
     * @author Fernando Matheus
     */
    public Page<UserDtoResponse> findAll(Pageable pageable, RepositoryFilter filter) {
        return userService.findAllFilter(pageable, filter);
    }

    /**
     * Consultar registro por Id.
     *
     * @param id Id a ser consultado
     * @return UserDtoResponse
     * @author Fernando Matheus
     */
    public UserDtoResponse findById(int id) {
        return userService.findById(id).orElseThrow(() -> this.messageResponse.errorNotFound());
    }


    /**
     * Criar novo registro de usuario.
     *
     * @param request  UserDtoRequest
     * @param response HttpServletResponse
     * @return ResponseEntity<MessageResponse>
     * @author Fernando Matheus
     */
    public ResponseEntity<MessageResponseHandler> create(UserDtoRequest request, HttpServletResponse response) {


        if (this.userService.checkUsernameExist(CharacterUtil.removeAllSpaces(request.getUsername()))) {
            throw this.messageResponse.errorUsernameExist();
        }


        if (this.contactService.checkPhoneExist(CharacterUtil.removeSpecialCharacters(request.getContact().getPhone()))) {
            throw this.messageResponse.errorPhoneExist();
        }

        if (this.contactService.checkEmailExist(request.getContact().getEmail())) {
            throw this.messageResponse.errorEmailExist();
        }

        var commit = this.userService.save(request);

        this.publisher.publishEvent(new ResourceEvent(this, response, commit.getId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(this.messageResponse.successCreate());

    }
}
