package com.lwj.springsecurity.filter;

import com.lwj.springsecurity.config.login.MyUserDetailsService;
import com.lwj.springsecurity.entity.JwtUser;
import com.lwj.springsecurity.entity.SysUser;
import com.lwj.springsecurity.service.SysUserService;
import com.lwj.springsecurity.service.impl.SysUserServiceImpl;
import com.lwj.springsecurity.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
@Component
public class JWTAuthorizationFilter extends OncePerRequestFilter {
    @Autowired
    private MyUserDetailsService userDetailsService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String tokenStr = request.getHeader(JwtUtils.TOKEN_HEADER);
        if (tokenStr != null && tokenStr.startsWith(JwtUtils.TOKEN_PREFIX)){
            String token = tokenStr.replace(JwtUtils.TOKEN_PREFIX,"");
            boolean isExpiration = JwtUtils.isExpiration(token);
            String username = JwtUtils.getUsername(token);
            JwtUser user = (JwtUser) userDetailsService.loadUserByUsername(username);
            if (username != null && !isExpiration && SecurityContextHolder.getContext().getAuthentication() == null){
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user,null,user.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        chain.doFilter(request,response);
    }


    private UsernamePasswordAuthenticationToken getAuthentication(String tokenHeader) {
        String token = tokenHeader.replace(JwtUtils.TOKEN_PREFIX,"");
        boolean isExpiration = JwtUtils.isExpiration(token);
        String username = JwtUtils.getUsername(token);
        JwtUser user = (JwtUser) userDetailsService.loadUserByUsername(username);
        if (username != null && !isExpiration){
            return new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword(), user.getAuthorities());
        }
        return null;
    }
}
