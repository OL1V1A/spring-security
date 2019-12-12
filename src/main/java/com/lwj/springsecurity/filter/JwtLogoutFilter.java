package com.lwj.springsecurity.filter;

import com.lwj.springsecurity.config.login.MyLogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

public class JwtLogoutFilter extends LogoutFilter {
    public JwtLogoutFilter(MyLogoutSuccessHandler logoutSuccessHandler, LogoutHandler...handlers) {
        super(logoutSuccessHandler, handlers);
    }

    @Override
    public void setFilterProcessesUrl(String filterProcessesUrl) {
        super.setFilterProcessesUrl("/logout");
    }

}
