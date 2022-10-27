package com.fmatheus.app.model.repository.query;

import com.fmatheus.app.model.entity.User;
import com.fmatheus.app.model.repository.filter.RepositoryFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserRepositoryQuery {

    Page<User> findAllFilter(Pageable pageable, RepositoryFilter filter);

    Long total(RepositoryFilter filter);

}
