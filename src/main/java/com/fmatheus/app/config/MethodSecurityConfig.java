package com.fmatheus.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * *
 * Classe de configuracao global. A anotacao @EnableGlobalMethodSecurity,
 * habilita a seguranca nos metodos.
 *
 * @author Fernando Matheus
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, proxyTargetClass = true)
public class MethodSecurityConfig extends GlobalMethodSecurityConfiguration {

    @Bean
    public AuthenticationManager authenticationManagerBean() {
        try {
            return super.authenticationManager();
        } catch (Exception ex) {
            Logger.getLogger(MethodSecurityConfig.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
