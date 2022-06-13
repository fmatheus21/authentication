package com.fmatheus.app.model.repository.impl;

import com.fmatheus.app.controller.enumerable.EntityEnum;
import com.fmatheus.app.controller.util.AppUtil;
import com.fmatheus.app.model.entity.Person;
import com.fmatheus.app.model.entity.User;
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

public class UserRepositoryImpl implements UserRepositoryQuery {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public Page<User> findAllFilter(Pageable pageable, RepositoryFilter filter) {
        var builder = manager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = builder.createQuery(User.class);
        Root<User> root = criteriaQuery.from(User.class);
        Join<Person, User> joinPerson = root.join(EntityEnum.ID_PERSON.getValue());
        Predicate[] predicates = createRestrictions(filter, builder, root);
        criteriaQuery
                .where(predicates)
                .orderBy(builder.asc(joinPerson.get(EntityEnum.NAME.getValue())));

        TypedQuery<User> typedQuery = manager.createQuery(criteriaQuery);

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
    private Predicate[] createRestrictions(RepositoryFilter filter, CriteriaBuilder builder, Root<User> root) {

        List<Predicate> predicates = new ArrayList<>();

        Join<Person, User> joinPerson = root.join(EntityEnum.ID_PERSON.getValue());

        if (Objects.nonNull(filter.getName())) {
            predicates.add(builder.like(builder.lower(joinPerson.get(EntityEnum.NAME.getValue())),
                    "%" + filter.getName().toLowerCase() + "%"));
        }

        if (Objects.nonNull(filter.getDocument())) {
            predicates.add(builder.like(builder.lower(joinPerson.get(EntityEnum.DOCUMENT.getValue())),
                    "%" + AppUtil.removeSpecialCharacters(filter.getDocument()) + "%"));
        }

        if (Objects.nonNull(filter.getEmail())) {
            predicates.add(builder.like(builder.lower(joinPerson.get(EntityEnum.EMAIL.getValue())),
                    "%" + filter.getEmail().toLowerCase() + "%"));
        }

        if (Objects.nonNull(filter.getUsername())) {
            predicates.add(builder.like(builder.lower(root.get(EntityEnum.USERNAME.getValue())),
                    "%" + filter.getUsername().toLowerCase() + "%"));
        }

        return predicates.toArray(new Predicate[0]);

    }


    /**
     * Metodo responsavel por criar paginacao.
     *
     * @param typedQuery - TypedQuery
     * @param pageable   - Pageable
     * @author Fernando Matheus
     */
    private void addPageRestrictions(TypedQuery<User> typedQuery, Pageable pageable) {
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
        Root<User> root = criteriaQuery.from(User.class);

        Predicate[] predicates = createRestrictions(filter, builder, root);
        criteriaQuery.where(predicates);

        criteriaQuery.select(builder.count(root));

        return manager.createQuery(criteriaQuery).getSingleResult();
    }


}
