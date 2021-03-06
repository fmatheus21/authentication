package com.fmatheus.app.controller.dto.response;

import lombok.*;


/**
 * @author Fernando Matheus
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PersonDtoResponse {

    private String name;
    private String document;

}
