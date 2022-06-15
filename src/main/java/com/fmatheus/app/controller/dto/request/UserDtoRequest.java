package com.fmatheus.app.controller.dto.request;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDtoRequest {

    @NotNull
    @NotBlank
    private String username;
    private PersonDtoRequest person;
    private ContactDtoRequest contact;
    private AddressDtoRequest address;

}
