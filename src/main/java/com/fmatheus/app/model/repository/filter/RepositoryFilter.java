package com.fmatheus.app.model.repository.filter;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String username;

}
