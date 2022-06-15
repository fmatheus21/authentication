package com.fmatheus.app.controller.converter.impl;

import com.fmatheus.app.controller.converter.PersonConverter;
import com.fmatheus.app.controller.dto.request.PersonDtoRequest;
import com.fmatheus.app.controller.dto.response.PersonDtoResponse;
import com.fmatheus.app.controller.enumerable.PersonTypeEnum;
import com.fmatheus.app.controller.util.AppUtil;
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
                .name(AppUtil.removeDuplicateSpace(AppUtil.convertAllUppercaseCharacters(request.getName())))
                .document(AppUtil.removeSpecialCharacters(request.getDocument()))
                .personType(PersonType.builder().id(request.getPersonType()).build())
                .build();
    }

    @Override
    public PersonDtoResponse converterToResponse(Person person) {
        person.setName(AppUtil.removeDuplicateSpace(AppUtil.convertFirstUppercaseCharacter(person.getName())));
        person.setDocument(person.getPersonType().getName().equalsIgnoreCase(PersonTypeEnum.PESSOA_FISICA.getValue()) ?
                AppUtil.formatCPF(person.getDocument()) : AppUtil.formatCNPJ(person.getDocument()));
        return this.modelMapper.map(person, PersonDtoResponse.class);
    }
}
