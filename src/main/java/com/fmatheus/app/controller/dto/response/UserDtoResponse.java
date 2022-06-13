package com.fmatheus.app.controller.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDtoResponse {

    private Integer id;
    private String username;
    private boolean active;
    private PersonDtoResponse person;
    private ContactDtoResponse contact;
    private AddressDtoResponse address;

}
