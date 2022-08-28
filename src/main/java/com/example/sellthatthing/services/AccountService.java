package com.example.sellthatthing.services;

import com.example.sellthatthing.datatransferobjects.NewAccountRequest;
import com.example.sellthatthing.datatransferobjects.UpdateAccountRequest;
import com.example.sellthatthing.datatransferobjects.VerificationDto;
import com.example.sellthatthing.emailsender.EmailSenderService;
import com.example.sellthatthing.exceptions.ResourceNotFoundException;
import com.example.sellthatthing.models.Account;
import com.example.sellthatthing.models.VerificationCode;
import com.example.sellthatthing.repositories.AccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

import static com.example.sellthatthing.emailsender.MailBody.VERIFY_CODE_HTML;

@Service
@AllArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;

    private final BCryptPasswordEncoder passwordEncoder;
    private final EmailSenderService emailSenderService;
    private final VerificationCodeService codeService;


    public Account findByAccountId(Long accountId) {
        return accountRepository.findById(accountId).orElseThrow(()
                -> new ResourceNotFoundException("Account id '" + accountId + "' was not found"));
    }

    public Account findByEmail(String email) {
        return accountRepository.findByEmail(email).orElseThrow(()
                -> new ResourceNotFoundException("Email: '" + email + "' was not found"));
    }

    @Transactional
    public VerificationDto createAccount(NewAccountRequest newAccountRequest) {
        String rawPassword = newAccountRequest.getPassword();
        newAccountRequest.setPassword(passwordEncoder.encode(newAccountRequest.getPassword()));

        Account newAccount = new Account(newAccountRequest);
        Long accountId = accountRepository.save(newAccount).getAccountId();
        Long codeId = sendVerificationCode(newAccount);

        return new VerificationDto(codeId, accountId, rawPassword);
    }

    private Long sendVerificationCode(Account newAccount) {
        VerificationCode code = new VerificationCode(newAccount);
        System.out.println(code.getCode());
        Long codeId = codeService.save(code).getCodeId();
        emailSenderService.sendMail(
                "SellThatThing: Activate your account",
                "donnotreply@sellyourthing.com",
                newAccount.getEmail(),
                "donotreply@sellyourthing.com",
                generateVerifyCodeBody(newAccount.getFirstName(), code.getCode())
        );
        return codeId;
    }

    private String generateVerifyCodeBody(String firstName, String verificationCode) {
        return VERIFY_CODE_HTML.replace("$firstName", firstName)
                .replace("$verificationCode", verificationCode);
    }

    @Transactional
    public void confirmVerificationCode(String enteredVerificationCode, Long verificationCodeId, Long accountId) {
        VerificationCode code = codeService.findByCodeId(verificationCodeId);
        Account account = findByAccountId(accountId);

        if (code.getConfirmedAt() != null) {
            throw new IllegalStateException("Account has already been verified");
        }
        if (code.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("Verification code has expired");
        }
        if (!code.getCode().equals(enteredVerificationCode)) {
            throw new IllegalStateException("Incorrect Verification Code");
        }
        if (!account.equals(code.getAccount())) {
            throw new IllegalStateException("Different Accounts, invalid request");
        }
        // update the confirmed at time, enable the users account, and delete the code
        codeService.updateConfirmedAtById(code.getCodeId());
        accountRepository.enableAppUserById(code.getAccount().getAccountId());
        codeService.deleteById(code.getCodeId());
    }

    public Account update(UpdateAccountRequest updateInfo, Long accountId) {
        Account accountToUpdate = findByAccountId(accountId);

        accountToUpdate.setFirstName(updateInfo.getFirstName());
        accountToUpdate.setLastName(updateInfo.getLastName());
        accountToUpdate.setEmail(updateInfo.getEmail());

        return accountRepository.save(accountToUpdate);
    }

    public void delete(Long accountId) {
        findByAccountId(accountId);// throws exception if not found
        accountRepository.deleteById(accountId);
    }

    public void doManualLogin(Long accountId, String rawPassword, HttpServletRequest request) {
        Account account = findByAccountId(accountId);
        if (passwordEncoder.matches(rawPassword, account.getPassword())) {
            try {
                request.login(account.getEmail(), rawPassword);
            }
            catch (ServletException e) {
                System.out.println("Login after account verification failed" + e);
            }
        }
        else {
            System.out.println("Got ya");
        }
    }
}
