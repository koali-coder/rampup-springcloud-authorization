package org.example.config;

import org.example.handle.SimpleAccessDeniedHandler;
import org.example.handle.SimpleAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity(debug = true)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceServerConfiguration {

    @Bean
    public SecurityFilterChain httpSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests().antMatchers(
                "/doc.html",
                "/swagger-resources/**",
                "/webjars/**",
                "/v3/api-docs"
        ).permitAll().anyRequest().authenticated();
        httpSecurity.sessionManagement().disable();
        httpSecurity.oauth2ResourceServer()
                .accessDeniedHandler(new SimpleAccessDeniedHandler())
                .authenticationEntryPoint(new SimpleAuthenticationEntryPoint())
                .jwt();
        return httpSecurity.build();
    }

}
