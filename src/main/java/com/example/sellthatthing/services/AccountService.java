package com.example.sellthatthing.services;

import com.example.sellthatthing.datatransferobjects.NewAccountRequest;
import com.example.sellthatthing.datatransferobjects.UpdateAccountRequest;
import com.example.sellthatthing.emailsender.EmailSenderService;
import com.example.sellthatthing.exceptions.ResourceNotFoundException;
import com.example.sellthatthing.models.Account;
import com.example.sellthatthing.models.VerificationCode;
import com.example.sellthatthing.repositories.AccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

import static com.example.sellthatthing.emailsender.MailBody.VERIFY_CODE_HTML;

@Service
@AllArgsConstructor
public class AccountService implements UserDetailsService {
    private final AccountRepository accountRepository;

    private final BCryptPasswordEncoder passwordEncoder;
    private final EmailSenderService emailSenderService;
    private final VerificationCodeService codeService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return accountRepository.findByEmail(email).orElseThrow(()
                -> new UsernameNotFoundException("Email '" + email + "' was not found"));
    }

    public Account findByAccountId(Long accountId) {
        return accountRepository.findById(accountId).orElseThrow(()
                -> new ResourceNotFoundException("Account id '" + accountId + "' was not found"));
    }

    public Account findByEmail(String email) {
        // throwing UsernameNotFound because of spring security
        return accountRepository.findByEmail(email).orElseThrow(()
                -> new UsernameNotFoundException("Email '" + email + "' was not found"));
    }

    @Transactional
    public Long createAccount(NewAccountRequest newAccountRequest) {
        Account newAccount = new Account(newAccountRequest, passwordEncoder);
        accountRepository.save(newAccount);
        // send verification code
        VerificationCode code = new VerificationCode(newAccount);
        codeService.save(code);
        emailSenderService.sendMail(
                "SellThatThing: Activate your account",
                "donnotreply@sellyourthing.com",
                newAccount.getEmail(),
                "donotreply@sellyourthing.com",
                generateVerifyCodeBody(newAccount.getFirstName(), code.getCode())
        );

        return code.getCodeId();
    }

    private String generateVerifyCodeBody(String firstName, String verificationCode) {
        return VERIFY_CODE_HTML.replace("$firstName", firstName)
                .replace("$verificationCode", verificationCode);
    }

    @Transactional
    public void confirmVerificationCode(String enteredVerificationCode, String verificationCodeId) {
        VerificationCode code = codeService.findByCodeId(Long.valueOf(verificationCodeId));

        if (code.getConfirmedAt() != null) {
            throw new IllegalStateException("Account has already been verified");
        }
        if (code.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("Verification code has expired");
        }
        if (!code.getCode().equals(enteredVerificationCode)) {
            throw new IllegalStateException("Incorrect Verification Code");
        }
        // update the confirmed at time, enable the users account, and delete the code
        codeService.updateConfirmedAtById(code.getCodeId());
        accountRepository.enableAppUserById(code.getAccount().getAccountId());
        codeService.deleteById(code.getCodeId());
    }

    public void manualAccountLogin(Account account, HttpServletRequest request) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(account, 23, account.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        request.getSession().setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());
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

    public void doManualLogin(String userEmail, String rawPassword, HttpServletRequest request) {
        try {
            request.login(userEmail, rawPassword);
        }
        catch (ServletException e) {
            System.out.println("Login error: " + e);
        }
    }
}
