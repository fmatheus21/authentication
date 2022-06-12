package com.fmatheus.app.model.service;

import java.util.List;
import java.util.Optional;

public interface GenericService<T, ID> {

    List<T> findAll();

    Optional<T> findById(ID id);

    <S extends T> S save(S s);

    void delete(T t);
}
