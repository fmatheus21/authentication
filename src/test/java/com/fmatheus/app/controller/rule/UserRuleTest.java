package com.fmatheus.app.controller.rule;

import com.fmatheus.app.controller.converter.UserConverter;
import com.fmatheus.app.controller.dto.response.UserDtoResponse;
import com.fmatheus.app.controller.enumerable.MessagesEnum;
import com.fmatheus.app.controller.exception.BadRequestException;
import com.fmatheus.app.model.entity.*;
import com.fmatheus.app.model.repository.UserRepository;
import com.fmatheus.app.model.repository.filter.RepositoryFilter;
import com.fmatheus.app.model.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class UserRuleTest {


    private final int id = 1;

    private User user;

    @Mock
    private MessageResponseRule messageResponseRule;

    @Mock
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserRule userRule;

    @Mock
    private UserConverter userConverter;

    @Mock
    private UserDtoResponse userDtoResponse;

    private Pageable pageable;

    private RepositoryFilter filter;

    private Page<UserDtoResponse> pageUserDtoResponse;


    @BeforeEach
    void setUp() {

        this.pageable = PageRequest.of(0, 1);

        filter = RepositoryFilter.builder()
                .name("Fernando")
                .build();

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

        this.user = User.builder()
                .id(1)
                .idPerson(person)
                .username("fmatheus")
                .password("$2a$10$LvtCtBtxJyrviMbU.C/Re.nfj3xRBbRVdbzNcgj8pjURJAN9XlIWC")
                .build();

        this.userDtoResponse = this.userConverter.converterToResponse(this.user);

        this.pageUserDtoResponse = this.collectionUsersPageable().map(map -> this.userConverter.converterToResponse(map));


    }

    @Test
    void findAll() {
        PageRequest pageRequest = PageRequest.of(1, this.collectionUsersPageable().getSize());
        Mockito.when(this.userRepository.findAllFilter(this.pageable, this.filter)).thenReturn(this.collectionUsersPageable());
        Mockito.when(this.userService.findAllFilter(this.pageable, this.filter)).thenReturn(this.collectionUsersPageable());
        Mockito.when(this.collectionUsersPageable().map(map -> this.userConverter.converterToResponse(map))).thenReturn(this.pageUserDtoResponse);
        var usersResponse = this.userRule.findAll(pageRequest, this.filter);
        assertTrue(Objects.nonNull(usersResponse));
    }

    @Test
    void findById() {
        Mockito.when(this.userRepository.findById(this.user.getId())).thenReturn(Optional.ofNullable(this.user));
        Mockito.when(this.userService.findById(this.user.getId())).thenReturn(Optional.ofNullable(this.user));
        Mockito.when(this.userConverter.converterToResponse(user)).thenReturn(this.userDtoResponse);
        var userResponse = this.userRule.findById(this.id);
        assertTrue(Objects.nonNull(userResponse));
    }

    @Test
    void create() {
    }

    private Page<User> collectionUsersPageable() {
        return new PageImpl<>(Collections.singletonList(this.user), pageable, 1L);
    }

    private Throwable badRequestException() {
        return assertThrows(BadRequestException.class, () -> {
            throw new BadRequestException(MessagesEnum.ERROR_NOT_FOUND);
        });
    }

}