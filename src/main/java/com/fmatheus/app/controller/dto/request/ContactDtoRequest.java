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
public class ContactDtoRequest {

    @NotNull
    @NotBlank
    private String phone;

    @NotNull
    @NotBlank
    private String email;

}
