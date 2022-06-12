package com.fmatheus.app.controller.security.config;

import com.fmatheus.app.controller.constant.PropertiesConstant;
import com.fmatheus.app.controller.security.token.CustomTokenEnhancer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.util.Arrays;

/**
 * Classe de autorizacao da aplicacao que ira consumir a api.
 *
 * @author Fernando Matheus
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Value(PropertiesConstant.TOKEN_VALIDITY_SECONDS)
    private int tokenValiditySeconds;

    @Value(PropertiesConstant.REFRESH_TOKEN_VALIDITY_SECONDS)
    private int refreshTokenValiditySeconds;

    @Value(PropertiesConstant.WITH_CLIENT)
    private String withClient;

    @Value(PropertiesConstant.SECRET_CLIENT)
    private String secretClient;

    @Value(PropertiesConstant.JWT_SIGNING_KEY)
    private String jwtSigningKey;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Metodo responsavel pela configuracao de autorizacao do cliente que consumira a api.
     *
     * @author Fernando Matheus
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient(this.withClient)
                .secret(this.passwordEncoder.encode(secretClient))
                .scopes("read", "write")
                .authorizedGrantTypes("password", "refresh_token")
                .accessTokenValiditySeconds(this.tokenValiditySeconds)
                .refreshTokenValiditySeconds(this.refreshTokenValiditySeconds);
    }

    /**
     * Metod resonsavel pela configuracao de seguranca e armazenamento JWT.
     *
     * @author Fernando Matheus
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        var chain = new TokenEnhancerChain();
        chain.setTokenEnhancers(Arrays.asList(this.tokenEnhancer(), this.accessTokenConverter()));
        endpoints
                .tokenStore(this.tokenStore())
                .tokenEnhancer(chain)
                .accessTokenConverter(this.accessTokenConverter())
                .reuseRefreshTokens(false)
                .authenticationManager(this.authenticationManager);
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        var accessTokenConverter = new JwtAccessTokenConverter();
        accessTokenConverter.setSigningKey(this.jwtSigningKey);
        return accessTokenConverter;
    }

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(this.accessTokenConverter());
    }

    @Bean
    public TokenEnhancer tokenEnhancer() {
        return new CustomTokenEnhancer();
    }

}
