package com.example.sellthatthing.services;

import com.example.sellthatthing.DTOs.NewAccountRequest;
import com.example.sellthatthing.DTOs.UpdateAccountRequest;
import com.example.sellthatthing.exceptions.EmptyResourceException;
import com.example.sellthatthing.exceptions.ResourceNotFoundException;
import com.example.sellthatthing.models.Account;
import com.example.sellthatthing.repositories.AccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;

    public List<Account> findAll() {
        List<Account> listOfAccounts = accountRepository.findAll();
        if (listOfAccounts.isEmpty()){
            throw new EmptyResourceException("No accounts found");
        }
        return listOfAccounts;
    }

    public Account findById(Long accountId) {
        return accountRepository.findById(accountId).orElseThrow(()
                -> new ResourceNotFoundException("Account id '" + accountId + "' was not found"));
    }

    public Account createAccount(NewAccountRequest newAccountRequest) {
        return accountRepository.save(
                new Account(
                        newAccountRequest.getFirstName(),
                        newAccountRequest.getLastName(),
                        newAccountRequest.getEmail(),
                        newAccountRequest.getDateOfBirth(),
                        newAccountRequest.getPassword()
                )
        );
    }

    public Account update(UpdateAccountRequest updateInfo, Long accountId) {
        Account accountToUpdate = findById(accountId);

        accountToUpdate.setFirstName(updateInfo.getFirstName());
        accountToUpdate.setLastName(updateInfo.getLastName());
        accountToUpdate.setEmail(updateInfo.getEmail());

        return accountRepository.save(accountToUpdate);
    }

    public void delete(Long accountId) {
        findById(accountId);// throws exception if not found
        accountRepository.deleteById(accountId);
    }

    public boolean existsByEmail(String email) {
        return accountRepository.existsByEmail(email);
    }
}
