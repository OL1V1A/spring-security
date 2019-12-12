package com.lwj.springsecurity.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lwj.springsecurity.config.login.MyAuthenticationFailHandler;
import com.lwj.springsecurity.config.login.MyAuthenticationSuccessHandler;
import com.lwj.springsecurity.entity.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
//@Component
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {


    private AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, MyAuthenticationSuccessHandler authenticationSuccessHandler,
                                   MyAuthenticationFailHandler authenticationFailHandler) {
//        this.setAuthenticationManager(authenticationManager);
        this.authenticationManager = authenticationManager;
        this.setAuthenticationSuccessHandler(authenticationSuccessHandler);
        this.setAuthenticationFailureHandler(authenticationFailHandler);
        super.setFilterProcessesUrl("/sys/login");
    }

//    @Override
//    public void setFilterProcessesUrl(String filterProcessesUrl) {
//        super.setFilterProcessesUrl("/sys/login");
//    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        try {
            LoginUser user = new ObjectMapper().readValue(request.getInputStream(),LoginUser.class);
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword())
            );
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
