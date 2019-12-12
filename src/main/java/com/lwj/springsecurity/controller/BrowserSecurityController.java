package com.lwj.springsecurity.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@RestController
public class BrowserSecurityController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private RequestCache requestCache = new HttpSessionRequestCache();

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    @Autowired
    private ObjectMapper objectMapper;


    @RequestMapping("/session/invalid")
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public void invalid(HttpServletRequest request, HttpServletResponse response) throws IOException {

        System.out.println("session过期了");
        SavedRequest savedRequest = requestCache.getRequest(request,response);
        if (savedRequest != null){
            String targetUrl = savedRequest.getRedirectUrl();
            System.out.println("引发session过期的请求为" + targetUrl);
        }


        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        Map<String,Object> map = new HashMap<>();
        map.put("message","session timeout");
        PrintWriter out = response.getWriter();
        out.write(objectMapper.writeValueAsString(map));
        out.flush();
        out.close();

    }

}
