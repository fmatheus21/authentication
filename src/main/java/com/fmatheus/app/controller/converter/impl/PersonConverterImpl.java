package com.fmatheus.app.controller.converter.impl;

import com.fmatheus.app.controller.converter.PersonConverter;
import com.fmatheus.app.controller.dto.request.PersonDtoRequest;
import com.fmatheus.app.controller.dto.response.PersonDtoResponse;
import com.fmatheus.app.controller.enumerable.PersonTypeEnum;
import com.fmatheus.app.controller.util.CharacterUtil;
import com.fmatheus.app.model.entity.Person;
import com.fmatheus.app.model.entity.PersonType;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PersonConverterImpl implements PersonConverter {

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public Person converterToRequest(PersonDtoRequest request) {
        return Person.builder()
                .name(CharacterUtil.removeDuplicateSpace(CharacterUtil.convertAllUppercaseCharacters(request.getName())))
                .document(CharacterUtil.removeSpecialCharacters(request.getDocument()))
                .personType(PersonType.builder().id(request.getPersonType()).build())
                .build();
    }

    @Override
    public PersonDtoResponse converterToResponse(Person person) {
        person.setName(CharacterUtil.removeDuplicateSpace(CharacterUtil.convertFirstUppercaseCharacter(person.getName())));
        person.setDocument(person.getPersonType().getName().equalsIgnoreCase(PersonTypeEnum.PESSOA_FISICA.getValue()) ?
                CharacterUtil.formatCPF(person.getDocument()) : CharacterUtil.formatCNPJ(person.getDocument()));
        return this.modelMapper.map(person, PersonDtoResponse.class);
    }
}
