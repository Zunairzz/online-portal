package com.ams.service;

import java.util.ArrayList;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;


@Component
public class CustomAuthenticationProvider
        implements AuthenticationProvider {

    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getPrincipal() + "";
        String password = authentication.getCredentials() + "";
        ArrayList<String> userRights = new ArrayList();
        userRights.add("ADMIN");
        return (Authentication) new UsernamePasswordAuthenticationToken(username, password, userRights);
    }


    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

    private boolean shouldAuthenticateAgainstThirdPartySystem() {
        return true;
    }

}