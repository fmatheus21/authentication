package com.fmatheus.app.controller.security.token;

import com.fmatheus.app.controller.constant.PropertiesConstant;
import com.fmatheus.app.controller.security.UserSecurity;
import com.fmatheus.app.controller.util.CharacterUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;
import java.util.Map;

public class CustomTokenEnhancer implements TokenEnhancer {

    @Value(PropertiesConstant.OPENAPI_VERSION)
    private String version;

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {

        var user = (UserSecurity) authentication.getPrincipal();

        Map<String, Object> info = new HashMap<>();
        info.put("name", CharacterUtil.convertFirstUppercaseCharacter(user.getUser().getIdPerson().getName()));
        info.put("firstName", CharacterUtil.returnFirstWord(CharacterUtil.convertFirstUppercaseCharacter(user.getUser().getIdPerson().getName())));
        info.put("username", CharacterUtil.convertAllLowercaseCharacters(user.getUsername()));
        info.put("version", version);

        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);

        return accessToken;
    }

}
