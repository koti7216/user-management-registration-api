package com.example.demo2.service;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.authentication.TokenExtractor;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@EnableWebSecurity
//@Configuration
public class WebConfig extends WebSecurityConfigurerAdapter {


   /*@Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
      return  http.authorizeRequests(requests->requests.mvcMatchers("/user/3rd/*").authenticated())
                        .oauth2ResourceServer().jwt().and().bearerTokenResolver(this::tokenExtractor).and().build();


    }*/



    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/user/4th").permitAll()
                .anyRequest().authenticated()
                .and()
            .oauth2ResourceServer()
                .jwt().and().bearerTokenResolver(this::tokenExtractor);
    }
    @Override
    public void configure(WebSecurity webSecurity) throws Exception {
        webSecurity.ignoring().antMatchers("/user/4th");}

    public String tokenExtractor(HttpServletRequest request) {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        Cookie cookie = WebUtils.getCookie(request, "token");
        if (cookie != null){
            System.out.println(cookie.getValue());
            return cookie.getValue();}
        return null;
    }



}
