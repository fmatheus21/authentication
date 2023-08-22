package com.fmatheus.app.controller.rule;

import com.fmatheus.app.controller.converter.UserConverter;
import com.fmatheus.app.controller.dto.request.AddressDtoRequest;
import com.fmatheus.app.controller.dto.request.ContactDtoRequest;
import com.fmatheus.app.controller.dto.request.PersonDtoRequest;
import com.fmatheus.app.controller.dto.request.UserDtoRequest;
import com.fmatheus.app.controller.dto.response.AddressDtoResponse;
import com.fmatheus.app.controller.dto.response.ContactDtoResponse;
import com.fmatheus.app.controller.dto.response.PersonDtoResponse;
import com.fmatheus.app.controller.dto.response.UserDtoResponse;
import com.fmatheus.app.controller.enumerable.MessagesEnum;
import com.fmatheus.app.controller.exception.BadRequestException;
import com.fmatheus.app.controller.exception.handler.MessageResponseHandler;
import com.fmatheus.app.controller.exception.message.MessageResponse;
import com.fmatheus.app.controller.util.CharacterUtil;
import com.fmatheus.app.model.entity.*;
import com.fmatheus.app.model.repository.ContactRepository;
import com.fmatheus.app.model.repository.PersonRepository;
import com.fmatheus.app.model.repository.UserRepository;
import com.fmatheus.app.model.repository.filter.RepositoryFilter;
import com.fmatheus.app.model.service.ContactService;
import com.fmatheus.app.model.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class UserRuleTest {


    @Mock
    private ApplicationEventPublisher publisher;

    @Mock
    private MessageResponse messageResponse;

    @Mock
    private UserService userService;

    @Mock
    private ContactService contactService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PersonRepository personRepository;

    @Mock
    private ContactRepository contactRepository;

    @InjectMocks
    private UserRule userRule;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private UserConverter userConverter;

    private Pageable pageable;

    @Mock
    private HttpServletResponse httpServletResponse;

    @Mock
    private MessageSource messageSource;


    @BeforeEach
    void setUp() {
        this.pageable = PageRequest.of(0, 1);
    }


    @Test
    void findAll() {

        var user = this.loadUser();
        var filter = RepositoryFilter.builder().username("fmatheus").build();
        var filterFind = RepositoryFilter.builder().username("fmatheus").build();

        var collectionPageUser = this.collectionPageUserDtoResponse();
        when(userService.findAllFilter(pageable, filterFind)).thenReturn(collectionPageUser);

        var converterToResponse = this.userDtoResponse();
        when(userConverter.converterToResponse(user)).thenReturn(converterToResponse);

        var actual = userRule.findAll(pageable, filter);
        var expected = this.collectionPageUser().map(map -> this.userConverter.converterToResponse(map));

        assertNotNull(actual);

    }

    @Test
    void findById() {

        var id = 1;
        var user = this.loadUser();

        var userOptional = Optional.of(this.loadUserDtoResponse());
        when(userService.findById(user.getId())).thenReturn(userOptional);

        var error = new BadRequestException(MessagesEnum.ERROR_NOT_FOUND);
        when(messageResponse.errorNotFound()).thenReturn(error);

        var converterToResponse = this.userDtoResponse();
        when(userConverter.converterToResponse(user)).thenReturn(converterToResponse);

        var actual = userRule.findById(id);
        var expected = this.loadUserDtoResponse();

        assertNotNull(actual);

    }

    @Test
    void create() {

        var request = this.postUserRequest();
        var user = this.loadUser();
        var phone = request.getContact().getPhone();
        var email = request.getContact().getEmail();
        var username = request.getUsername();

        var usernameValue = user.getUsername().equalsIgnoreCase(username);
        when(userService.checkUsernameExist(CharacterUtil.removeAllSpaces(user.getUsername()))).thenReturn(usernameValue);
        when(messageResponse.errorUsernameExist()).thenReturn(new BadRequestException(MessagesEnum.ERROR_USERNAME_EXIST));

        var findByPhoneValue = user.getIdPerson().getContact().getPhone().equalsIgnoreCase(phone);
        when(contactService.checkPhoneExist(CharacterUtil.removeSpecialCharacters(phone))).thenReturn(findByPhoneValue);
        when(messageResponse.errorPhoneExist()).thenReturn(new BadRequestException(MessagesEnum.ERROR_PHONE_EXIST));

        var findByEmailValue = user.getIdPerson().getContact().getEmail().equalsIgnoreCase(email);
        when(contactService.checkEmailExist(email)).thenReturn(findByEmailValue);
        when(messageResponse.errorEmailExist()).thenReturn(new BadRequestException(MessagesEnum.ERROR_EMAIL_EXIST));

        when(userConverter.converterToSave(request)).thenReturn(user.getIdPerson());

        when(userService.save(request)).thenReturn(this.loadUserDtoResponse());

        MessagesEnum messagesEnum = MessagesEnum.SUCCESS_CREATE;
        String message = messageSource.getMessage(messagesEnum.getMessage(), null, LocaleContextHolder.getLocale());
        when(messageResponse.successCreate()).thenReturn(new MessageResponseHandler(messagesEnum, messagesEnum.getHttpSttus().getReasonPhrase(), message));

        ResponseEntity<MessageResponseHandler> actual = userRule.create(request, httpServletResponse);

        ResponseEntity<MessageResponseHandler> expected = ResponseEntity.status(HttpStatus.CREATED).body(this.messageResponse.successCreate());

        assertEquals(expected, actual);

    }


    private UserDtoRequest postUserRequest() {
        return UserDtoRequest.builder()
                .username("fnunes")
                .person(PersonDtoRequest.builder()
                        .name("Felipe Nunes")
                        .document("67780886050")
                        .personType(1)
                        .build())
                .contact(ContactDtoRequest.builder()
                        .email("felipe.nunes@domain.com")
                        .phone("21981247167")
                        .build())
                .address(AddressDtoRequest.builder()
                        .place("Avenida das Américas")
                        .number("12000")
                        .complement("Apt 401")
                        .district("Barra da Tijuca")
                        .city("Rio de Janeiro")
                        .state("RJ")
                        .zipCode("22793082")
                        .build())
                .build();
    }


    private UserDtoResponse loadUserDtoResponse() {
        return UserDtoResponse.builder()
                .id(1)
                .username("fmatheus")
                .active(true)
                .person(PersonDtoResponse.builder()
                        .name("Fernando Matheus")
                        .document("67780886050")
                        .build())
                .contact(ContactDtoResponse.builder()
                        .email("contact@domain.com")
                        .phone("21986731552")
                        .build())
                .address(AddressDtoResponse.builder()
                        .place("Avenida das Américas")
                        .number("12000")
                        .complement("Apt 401")
                        .district("Barra da Tijuca")
                        .city("Rio de Janeiro")
                        .state("RJ")
                        .zipCode("22793082")
                        .build())
                .build();
    }


    private User loadUser() {

        var person = Person.builder()
                .id(1)
                .personType(PersonType.builder().id(1).build())
                .name("Fernando Matheus")
                .document("67780886050")
                .build();

        var contact = Contact.builder()
                .id(1)
                .person(person)
                .email("contact@domain.com")
                .phone("21986731552")
                .build();

        var address = Address.builder()
                .id(1)
                .person(person)
                .place("Avenida das Américas")
                .number("12000")
                .complement("Apt 401")
                .district("Barra da Tijuca")
                .city("Rio de Janeiro")
                .state("RJ")
                .zipCode("22793082")
                .build();

        person.setContact(contact);
        person.setAddress(address);

        return User.builder()
                .id(1)
                .idPerson(person)
                .username("fmatheus")
                .password("$2a$10$LvtCtBtxJyrviMbU.C/Re.nfj3xRBbRVdbzNcgj8pjURJAN9XlIWC")
                .build();
    }


    private UserDtoResponse userDtoResponse() {
        return this.userConverter.converterToResponse(this.loadUser());
    }

    private Page<User> collectionPageUser() {
        return new PageImpl<>(Collections.singletonList(this.loadUser()));
    }


    private Page<UserDtoResponse> collectionPageUserDtoResponse() {
        return new PageImpl<>(Collections.singletonList(this.loadUserDtoResponse()));
    }

}