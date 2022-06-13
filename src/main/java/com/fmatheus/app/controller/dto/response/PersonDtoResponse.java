package com.fmatheus.app.controller.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fmatheus.app.controller.util.AppUtil;
import com.fmatheus.app.model.entity.PersonEntity;
import lombok.*;


/**
 * @author Fernando Matheus
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonPropertyOrder({"name", "firstName", "document", "typePerson"})
public class PersonDtoResponse {

    @JsonProperty("name")
    private String name;

    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("document")
    private String document;

    @JsonProperty("typePerson")
    private String typePerson;


    /**
     * Converte uma entidade em dto.
     *
     * @param entity - Entidade
     * @return PersonDtoResponse
     * @author Fernando Matheus
     */
    public static PersonDtoResponse converterDto(PersonEntity entity) {
        var count = AppUtil.countCharacter(entity.getDocument());
        var document = count > 13 ? AppUtil.formatCNPJ(entity.getDocument()) : AppUtil.formatCPF(entity.getDocument());
        return PersonDtoResponse.builder()
                .name(AppUtil.convertFirstUppercaseCharacter(entity.getName()))
                .firstName(AppUtil.convertFirstUppercaseCharacter(AppUtil.returnFirstWord(entity.getName())))
                .document(document)
                .typePerson(AppUtil.convertFirstUppercaseCharacter(entity.getPersonTypeEntity().getName()))
                .build();
    }


}
