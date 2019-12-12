package com.lwj.springsecurity.config.login;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@Component(value = "myAuthenticationFailHandler")
public class MyAuthenticationFailHandler implements AuthenticationFailureHandler  {
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        Map<String,Object> map = new HashMap<>();
        map.put("code",401);
        if (e instanceof UsernameNotFoundException){
            map.put("message","用户名不存在");
        }else if (e instanceof BadCredentialsException){
            map.put("message","用户名或密码不正确");
        }else if (e instanceof DisabledException){
            map.put("message","账号被禁用");
        }else{
            map.put("message","登录失败");
        }

        response.setContentType("application/json;charset=UTF-8");

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        PrintWriter out = response.getWriter();
        out.write(objectMapper.writeValueAsString(map));
        out.flush();
        out.close();
    }
}
