package com.lwj.springsecurity.config;

import com.lwj.springsecurity.config.login.MyAuthenticationFailHandler;
import com.lwj.springsecurity.config.login.MyAuthenticationSuccessHandler;
import com.lwj.springsecurity.config.login.MyLogoutSuccessHandler;
import com.lwj.springsecurity.filter.JWTAuthenticationFilter;
import com.lwj.springsecurity.filter.JWTAuthorizationFilter;
import com.lwj.springsecurity.filter.JwtLogoutFilter;
import com.lwj.springsecurity.config.login.MyLogoutHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class MySecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private MyAuthenticationSuccessHandler authenticationSuccessHandler;
    @Autowired
    private MyAuthenticationFailHandler authenticationFailHandler;
    @Autowired
    private MyLogoutSuccessHandler logoutSuccessHandler;
    @Autowired
    private MyLogoutHandler logoutHandler;
//    @Autowired
//    private MyAuthenticationManager authenticationManager;
//    @Autowired
//    private JWTAuthenticationFilter jwtAuthenticationFilter;
    @Autowired
    private JWTAuthorizationFilter jwtAuthorizationFilter;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    public static void main(String[] args) {
        String user = "user";
        PasswordEncoder p = new BCryptPasswordEncoder();
        String pwd = p.encode(user);
        System.out.println(pwd);
    }

//    @Bean


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()

                .authorizeRequests()
                .antMatchers("/user/json","/session/invalid")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .addFilter(new JWTAuthenticationFilter(authenticationManager(),authenticationSuccessHandler,authenticationFailHandler))
                .addFilterAfter(jwtAuthorizationFilter,JWTAuthenticationFilter.class)
                .addFilter(new JwtLogoutFilter(logoutSuccessHandler,logoutHandler))

//                .formLogin()
////                .loginProcessingUrl("/login")
////                .permitAll()
//                .and()
//                .logout()
//                .logoutRequestMatcher(new AntPathRequestMatcher("/sys/logout","POST"))
////                .logoutSuccessHandler(logoutSuccessHandler)
//                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .invalidSessionUrl("/session/invalid")
//                .maximumSessions(1)
                ;
    }

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.authenticationProvider(myAuthenticationProvider);
//    }
}
