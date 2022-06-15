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
public class AddressDtoRequest {

    @NotNull
    @NotBlank
    private String place;

    @NotNull
    @NotBlank
    private String number;

    private String complement;

    @NotNull
    @NotBlank
    private String district;

    @NotNull
    @NotBlank
    private String city;

    @NotNull
    @NotBlank
    private String state;

    @NotNull
    @NotBlank
    private String zipCode;

}
