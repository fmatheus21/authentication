package com.fmatheus.app.controller.dto.response;

import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContactDtoResponse {

    private String phone;
    private String email;

}
