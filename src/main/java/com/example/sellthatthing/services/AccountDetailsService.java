package com.example.sellthatthing.services;

import com.example.sellthatthing.models.AccountDetails;
import com.example.sellthatthing.repositories.AccountRepository;
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
                accountRepository.findByEmail(email).orElseThrow(()
                        -> new UsernameNotFoundException("Email '" + email + "' was not found")));
    }
}
