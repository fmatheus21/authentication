package com.fmatheus.app.controller.security;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.io.Serial;
import java.util.Collection;

public class UserSecurity extends User {

    @Serial
    private static final long serialVersionUID = 1L;

    @Getter
    private final com.fmatheus.app.model.entity.User user;

    public UserSecurity(com.fmatheus.app.model.entity.User user, Collection<? extends GrantedAuthority> authorities) {
        super(user.getUsername(), user.getPassword(), authorities);
        this.user = user;
    }

}
