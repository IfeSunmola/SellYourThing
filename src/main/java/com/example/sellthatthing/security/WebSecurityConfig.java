package com.example.sellthatthing.security;

import com.example.sellthatthing.services.AccountDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
    private final AccountDetailsService accountDetailsService;
    private final BCryptPasswordEncoder passwordEncoder;

    // links that does not need authentication
    private static final String[] ANT_WHITELIST = {"/", "/index", "/register/**", "/users/**", "/posts/sort"};
    private static final String[] REGEX_WHITELIST = {"/posts/(\\d+)"}; // for posts/postId
    private static final String[] RESOURCES_WHITELIST = {"/css/**", "/images/**", "/js/**", "/h2-console/**"};

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
                .loginProcessingUrl("/login").defaultSuccessUrl("/", true)
                .usernameParameter("email").passwordParameter("password").failureUrl("/login/login-error")
                .and()
                // logout
                .logout().logoutUrl("/logout").logoutSuccessUrl("/login?logout")
                .invalidateHttpSession(true).permitAll();
        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(accountDetailsService); // implements UserDetailsService
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }
}
