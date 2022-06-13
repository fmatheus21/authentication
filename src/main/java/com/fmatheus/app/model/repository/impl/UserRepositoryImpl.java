package com.fmatheus.app.model.repository.impl;

import com.fmatheus.app.controller.enumerable.EntityEnum;
import com.fmatheus.app.model.entity.PersonEntity;
import com.fmatheus.app.model.entity.UserEntity;
import com.fmatheus.app.model.repository.filter.RepositoryFilter;
import com.fmatheus.app.model.repository.query.UserRepositoryQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Fernando Matheus
 */
public class UserRepositoryImpl implements UserRepositoryQuery {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public Page<UserEntity> findAllFilter(Pageable pageable, RepositoryFilter filter) {
        var builder = manager.getCriteriaBuilder();
        CriteriaQuery<UserEntity> criteriaQuery = builder.createQuery(UserEntity.class);
        Root<UserEntity> root = criteriaQuery.from(UserEntity.class);
        Join<PersonEntity, UserEntity> joinPerson = root.join(EntityEnum.ID_PERSON.getValue());
        Predicate[] predicates = createRestrictions(filter, builder, root);
        criteriaQuery
                .where(predicates)
                .orderBy(builder.asc(joinPerson.get(EntityEnum.NAME.getValue())));

        TypedQuery<UserEntity> typedQuery = manager.createQuery(criteriaQuery);

        this.addPageRestrictions(typedQuery, pageable);

        return new PageImpl<>(typedQuery.getResultList(), pageable, total(filter));
    }


    /**
     * Metodo responsavel por criar uma array de Predicate para implementar uma
     * consulta de acordo com o filtro.
     *
     * @param filter  - Filtro de consulta
     * @param builder - CriteriaBuilder
     * @param root    - Root
     * @return Predicate[]
     * @author Fernando Matheus
     */
    private Predicate[] createRestrictions(RepositoryFilter filter, CriteriaBuilder builder,
                                           Root<UserEntity> root) {

        List<Predicate> predicates = new ArrayList<>();

        Join<PersonEntity, UserEntity> joinPerson = root.join(EntityEnum.ID_PERSON.getValue());

        if (Objects.nonNull(filter.getName())) {
            predicates.add(builder.like(builder.lower(joinPerson.get(EntityEnum.NAME.getValue())),
                    "%" + filter.getName().toLowerCase() + "%"));
        }

        if (Objects.nonNull(filter.getDocument())) {
            predicates.add(builder.like(builder.lower(joinPerson.get(EntityEnum.DOCUMENT.getValue())),
                    "%" + filter.getDocument().toLowerCase() + "%"));
        }

        if (Objects.nonNull(filter.getUsername())) {
            predicates.add(builder.like(builder.lower(root.get(EntityEnum.USERNAME.getValue())),
                    "%" + filter.getUsername().toLowerCase() + "%"));
        }

        return predicates.toArray(new Predicate[predicates.size()]);

    }


    /**
     * Metodo responsavel por criar paginacao.
     *
     * @param typedQuery - TypedQuery
     * @param pageable   - Pageable
     * @author Fernando Matheus
     */
    private void addPageRestrictions(TypedQuery<UserEntity> typedQuery, Pageable pageable) {
        int currentPage = pageable.getPageNumber();
        int totalRecordsPerPage = pageable.getPageSize();
        int firstPageRecord = currentPage * totalRecordsPerPage;

        typedQuery.setFirstResult(firstPageRecord);
        typedQuery.setMaxResults(totalRecordsPerPage);
    }


    /**
     * Metodo responsavel por contar o total de registros.
     *
     * @param filter - Filtro de consulta
     * @return Long
     * @author Fernando Matheus
     */
    private Long total(RepositoryFilter filter) {
        var builder = manager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
        Root<UserEntity> root = criteriaQuery.from(UserEntity.class);

        Predicate[] predicates = createRestrictions(filter, builder, root);
        criteriaQuery.where(predicates);

        criteriaQuery.select(builder.count(root));

        return manager.createQuery(criteriaQuery).getSingleResult();
    }


}
