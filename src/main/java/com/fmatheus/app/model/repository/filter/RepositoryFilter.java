package com.fmatheus.app.model.repository.filter;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Classe que contem os atributos de um filtro que sao utilizados em consultas.
 *
 * @author Fernando Matheus
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RepositoryFilter {

    private int idClient;
    private String name;
    private String document;
    private String email;
    private String phone;
    private String global;
    private String plate;
    private String renavam;
    private String username;
    private String nameConsultant;
    private int idConsultant;
    private LocalDate dateSignedStart;
    private LocalDate dateSignedEnd;
}
