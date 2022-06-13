package com.fmatheus.app.model.repository;

import com.fmatheus.app.model.entity.User;
import com.fmatheus.app.model.repository.filter.RepositoryFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);

    Page<User> findAllFilter(Pageable pageable, RepositoryFilter filter);

}