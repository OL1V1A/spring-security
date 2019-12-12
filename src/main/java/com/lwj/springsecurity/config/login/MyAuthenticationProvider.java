package com.lwj.springsecurity.config.login;

import com.lwj.springsecurity.entity.JwtUser;
import com.lwj.springsecurity.entity.SysUser;
import com.lwj.springsecurity.config.login.MyUserDetailsService;
import com.lwj.springsecurity.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

//@Component
public class MyAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private MyUserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();
        JwtUser user = (JwtUser) userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword(),user.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(UsernamePasswordAuthenticationToken.class);
    }
}
