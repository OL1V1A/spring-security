package com.lwj.springsecurity.config.login;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lwj.springsecurity.entity.JwtUser;
import com.lwj.springsecurity.entity.SysUser;
import com.lwj.springsecurity.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@Component(value = "myAuthenticationSuccessHandler")
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        JwtUser user = (JwtUser) authentication.getPrincipal();
        String token = JwtUtils.generateJsonWebToken(user);
        String tokenStr = JwtUtils.TOKEN_PREFIX + token;
        response.setHeader("token",tokenStr);

        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpStatus.OK.value());
        Map<String,Object> map = new HashMap<>(16);
        map.put("message","登陆成功");
        map.put("token",tokenStr);
        map.put("user",user.getUsername());
        map.put("code",200);
        PrintWriter out = response.getWriter();
        out.write(objectMapper.writeValueAsString(map));
        out.flush();
        out.close();

    }
}
