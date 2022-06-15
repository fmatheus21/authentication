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
public class PersonDtoRequest {

    @NotNull
    @NotBlank
    private String name;

    @NotNull
    @NotBlank
    private String document;

    @NotNull
    private int personType;
}
