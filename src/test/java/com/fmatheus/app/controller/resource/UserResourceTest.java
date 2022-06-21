package com.fmatheus.app.controller.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fmatheus.app.controller.constant.ResourceConstant;
import com.fmatheus.app.controller.constant.RoleConstant;
import com.fmatheus.app.controller.converter.UserConverter;
import com.fmatheus.app.controller.dto.request.AddressDtoRequest;
import com.fmatheus.app.controller.dto.request.ContactDtoRequest;
import com.fmatheus.app.controller.dto.request.PersonDtoRequest;
import com.fmatheus.app.controller.dto.request.UserDtoRequest;
import com.fmatheus.app.controller.rule.MessageResponseRule;
import com.fmatheus.app.controller.rule.UserRule;
import com.fmatheus.app.model.entity.*;
import com.fmatheus.app.model.repository.filter.RepositoryFilter;
import com.fmatheus.app.model.service.PersonService;
import com.fmatheus.app.model.service.UserService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(UserResource.class)
class UserResourceTest {

    private static final String URL_REQUEST = ResourceConstant.USERS + "/";

    private static final String USER = "ADMIN";

    @Autowired
    private WebApplicationContext context;

    protected MockMvc mockMvc;

    @MockBean
    private UserRule userRule;

    @MockBean
    private MessageResponseRule messageResponseRule;

    @MockBean
    private UserConverter userConverter;

    @MockBean
    private UserService userService;

    @MockBean
    private PersonService personService;

    @MockBean
    private ModelMapper modelMapper;

    @MockBean
    private Pageable pageable;

    @MockBean
    private MessageSource messageSource;

    @Autowired
    private ObjectMapper mapper;

    UserResourceTest() {
    }

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).apply(SecurityMockMvcConfigurers.springSecurity()).build();
        this.pageable = PageRequest.of(0, 1);
    }

    @SneakyThrows
    @Test
    void list() {
        var page = this.collectionUsersPageable();
        var pageRequest = PageRequest.of(1, page.getSize());
        var filter = RepositoryFilter.builder().name("fegvfggdsgr").build();

        Mockito.when(this.userService.findAllFilter(pageRequest, filter)).thenReturn(page);
        MvcResult mvcResult = this.mockMvc.perform(get(URL_REQUEST)
                        .with(SecurityMockMvcRequestPostProcessors.user(USER).roles(RoleConstant.VIEW_USER))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String result = mvcResult.getResponse().getContentAsString();
        assertNotNull(result);
        assertEquals(this.okSuccess().getStatusCode().value(), mvcResult.getResponse().getStatus());
    }

    @SneakyThrows
    @Test
    void findById() {

        when(this.userService.findById(1)).thenReturn(this.loadOptionalUser());

        MvcResult mvcResult = this.mockMvc.perform(get(URL_REQUEST + 10)
                        .with(SecurityMockMvcRequestPostProcessors.user(USER).roles(RoleConstant.VIEW_USER))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String result = mvcResult.getResponse().getContentAsString();
        assertNotNull(result);
        assertEquals(this.okSuccess().getStatusCode().value(), mvcResult.getResponse().getStatus());

    }

    @SneakyThrows
    @Test
    void create() {

        var person = this.loadUser().getIdPerson();
        var request = this.postUserRequest();

        when(this.personService.save(person)).thenReturn(person);

        MvcResult mvcResult = this.mockMvc.perform(post(URL_REQUEST)
                        .with(SecurityMockMvcRequestPostProcessors.user(RoleConstant.CREATE_USER))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.mapper.writeValueAsString(request)))
                .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String result = mvcResult.getResponse().getContentAsString();
        assertNotNull(result);
        assertEquals(this.createSuccess().getStatusCode().value(), mvcResult.getResponse().getStatus());
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

    /**
     * Objeto que representa os dados que vem no corpo da requisicao.
     *
     * @return UserDtoRequest
     * @author Fernando Matheus
     */
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

    private Optional<User> loadOptionalUser() {
        return Optional.of(this.loadUser());
    }

    private Page<User> collectionUsersPageable() {
        return new PageImpl<>(Collections.singletonList(this.loadUser()));
    }


    private ResponseEntity<Object> createSuccess() {
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    private ResponseEntity<Object> okSuccess() {
        return ResponseEntity.status(HttpStatus.OK).build();
    }


}