package com.fmatheus.app.controller.security;

import com.fmatheus.app.model.entity.User;
import com.fmatheus.app.model.repository.UserRepository;
import com.fmatheus.app.rule.MessageResponseRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static com.fmatheus.app.controller.util.AppUtil.removeSpecialCharacters;


@Service
public class AppUserDetailsService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(AppUserDetailsService.class);

    @Autowired
    private MessageResponseRule messageResponseRule;

    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        logger.info("Username: {}", username);
        var user = this.userRepository.findByUsername(removeSpecialCharacters(username)).orElseThrow(() -> new UsernameNotFoundException("Usuário ou senha incorretos."));

        if (!user.isActive()) {
            logger.error("Usuario inativo: {}", username);
            throw this.messageResponseRule.userInactiveException();
        }

        logger.error("Usuario autorizado: {}", username);
        return new UserSecurity(user, this.authorities(user));
    }

    private Collection<? extends GrantedAuthority> authorities(User user) {
        Set<SimpleGrantedAuthority> authoritys = new HashSet<>();
        user.getRoleCollection().forEach(p -> authoritys.add(new SimpleGrantedAuthority(p.getRole().toUpperCase())));
        return authoritys;
    }

}
