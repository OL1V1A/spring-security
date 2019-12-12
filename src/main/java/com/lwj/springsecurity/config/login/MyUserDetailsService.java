package com.lwj.springsecurity.config.login;

import com.lwj.springsecurity.entity.JwtUser;
import com.lwj.springsecurity.entity.SysUser;
import com.lwj.springsecurity.service.SysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private  Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SysUserService sysUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        SysUser user = sysUserService.selectByUsername(username);
        return new JwtUser(user);
    }
}
