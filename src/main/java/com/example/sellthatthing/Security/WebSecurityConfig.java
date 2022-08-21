package com.example.sellthatthing.Security;

import com.example.sellthatthing.Account.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@AllArgsConstructor
@Configuration
public class WebSecurityConfig {
    private final AccountService accountService;
    private final BCryptPasswordEncoder passwordEncoder;

    // links that does not need authentication
    private static final String[] WHITELIST = {"/", "/index", "/register/**", "/posts/**", "/users/**"};
    private static final String[] RESOURCES_WHITELIST = {"/images/**", "/h2-console/**"};

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // .anyRequest().authenticated() -> require authentication for any path that was not ant matched
        http
                // h2 console
                .csrf().disable().headers().frameOptions().disable()
                .and()
                .authorizeRequests()
                .antMatchers(WHITELIST).permitAll()
                .antMatchers("/admin/**").hasRole("Admin").anyRequest().authenticated()
                .and()
                //login
                .formLogin().loginPage("/login").permitAll().loginProcessingUrl("/login")
                .usernameParameter("email").passwordParameter("password");
        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        // RESOURCES_WHITELIST shouldn't be checked in security
        return (web -> web.ignoring().antMatchers(RESOURCES_WHITELIST));
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(accountService); // implements UserDetailsService
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }
}
