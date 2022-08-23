package com.example.sellthatthing.security;

import com.example.sellthatthing.services.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@AllArgsConstructor
@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfig {
    private final AccountService accountService;
    private final BCryptPasswordEncoder passwordEncoder;

    // links that does not need authentication
    private static final String[] ANT_WHITELIST = {"/", "/index", "/register/**", "/users/**"};
    private static final String[] REGEX_WHITELIST = {"/posts/(\\d+)"};
    private static final String[] RESOURCES_WHITELIST = {"/images/**", "/h2-console/**"};

    @Bean
    public SecurityFilterChain filterChain(final HttpSecurity http) throws Exception {
        http
                // h2 console
                .csrf().disable().headers().frameOptions().disable()
                .and()
                // views security
                .authorizeRequests()
                .antMatchers(ANT_WHITELIST).permitAll()
                .antMatchers(RESOURCES_WHITELIST).permitAll()
                .regexMatchers(REGEX_WHITELIST).permitAll()
                // roles security
                .antMatchers("/admin/**").hasRole("Admin")
                // any other endpoint not specified should require authentication
                .anyRequest().authenticated()
                .and()
                // login
                .formLogin().loginPage("/login").permitAll()
                .loginProcessingUrl("/login").defaultSuccessUrl("/", false)
                .usernameParameter("email").passwordParameter("password")
                .and()
                // logout
                .logout().logoutUrl("/logout").logoutSuccessUrl("/login?logout")
                .invalidateHttpSession(true).permitAll();
        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(accountService); // implements UserDetailsService
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }
}
