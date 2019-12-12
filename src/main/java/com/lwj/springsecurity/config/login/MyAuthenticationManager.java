package com.lwj.springsecurity.config.login;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.Objects;

//@Component
public class MyAuthenticationManager implements AuthenticationManager {

    private final AuthenticationProvider authenticationProvider;

    public MyAuthenticationManager(AuthenticationProvider authenticationProvider) {
        this.authenticationProvider = authenticationProvider;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Authentication result = authenticationProvider.authenticate(authentication);
        if (Objects.nonNull(result)){
            return result;
        }
        throw new ProviderNotFoundException("Authentication failed!");
    }
}
