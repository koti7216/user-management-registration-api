package com.example.demo2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@EnableWebSecurity
@Configuration
public class SpringSecurityConfiguration {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        return http.csrf().disable().authorizeRequests((authorize)-> authorize.antMatchers("/user/4th","/user/1st","/user/6th").permitAll().anyRequest().authenticated())
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt).build();
    }
   /* p
   ublic String tokenExtractor(HttpServletRequest request) {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header != null)
            return header.replace("Bearer ", "");
        Cookie cookie = WebUtils.getCookie(request, "token");
        if (cookie != null)
            return cookie.getValue();
        return null;
    }*/
 /* @Bean
   CorsConfigurationSource corsConfigurationSource() {

       CorsConfiguration configuration = new CorsConfiguration();
       configuration.setAllowCredentials(true);
       configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
       configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
       configuration.setAllowedHeaders(Arrays.asList("X-Requested-With","Origin","Content-Type","Accept","Set-Cache","Authorization"));

       // This allow us to expose the headers
       configuration.setExposedHeaders(Arrays.asList("Access-Control-Allow-Headers", "Authorization, x-xsrf-token, Access-Control-Allow-Headers, Origin, Accept, X-Requested-With, " +
               "Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers"));

       UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
       source.registerCorsConfiguration("/**", configuration);
       return source;
   }*/
}
