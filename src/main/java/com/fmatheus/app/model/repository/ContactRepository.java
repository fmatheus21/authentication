package com.fmatheus.app.model.repository;

import com.fmatheus.app.model.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Integer> {

    boolean findByEmail(String email);

    boolean findByPhone(String phone);

}