package com.example.sellyourthing.services;

import com.example.sellyourthing.models.AccountDetails;
import com.example.sellyourthing.repositories.AccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AccountDetailsService implements UserDetailsService {
    private final AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return new AccountDetails(
                accountRepository.findByEmailIgnoreCase(email).orElseThrow(()
                        -> new UsernameNotFoundException("Email '" + email + "' was not found")));
    }
}
