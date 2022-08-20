package com.example.sellthatthing.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class WebSecurityConfig {
    // links that does not need authentication
    private static final String[] WHITELIST = {"/", "/index", "/register/**", "/email"};
    private static final String[] RESOURCES_WHITELIST = {"/images/**", "/h2-console/**"};

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests().antMatchers(WHITELIST).permitAll().anyRequest().authenticated();

        http
                .formLogin().loginPage("/login").loginProcessingUrl("/login")
                .usernameParameter("email").passwordParameter("password").permitAll();

        // for h2 console
        http.csrf().disable();
        http.headers().frameOptions().disable();
        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        // RESOURCES_WHITELIST shouldn't be checked in security
        return (web -> web.ignoring().antMatchers(RESOURCES_WHITELIST));
    }
}
