package com.example.sellthatthing.services;

import com.example.sellthatthing.datatransferobjects.NewAccountRequest;
import com.example.sellthatthing.datatransferobjects.UpdateAccountRequest;
import com.example.sellthatthing.datatransferobjects.VerificationDto;
import com.example.sellthatthing.emailsender.EmailSenderService;
import com.example.sellthatthing.exceptions.ResourceNotFoundException;
import com.example.sellthatthing.models.Account;
import com.example.sellthatthing.models.AccountDetails;
import com.example.sellthatthing.models.VerificationCode;
import com.example.sellthatthing.repositories.AccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.HashMap;
import java.util.List;

import static com.example.sellthatthing.emailsender.MailBody.VERIFY_CODE_HTML;

@Service
@AllArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;

    private final BCryptPasswordEncoder passwordEncoder;
    private final EmailSenderService emailSenderService;
    private final VerificationCodeService codeService;
    private static final int MIN_AGE = 16;

    public Account findByAccountId(Long accountId) {
        return accountRepository.findById(accountId).orElseThrow(()
                -> new ResourceNotFoundException("Account id '" + accountId + "' was not found"));
    }

    public Account findByEmail(String email) {
        return accountRepository.findByEmailIgnoreCase(email).orElseThrow(()
                -> new ResourceNotFoundException("Email: '" + email + "' was not found"));
    }

    public void checkForErrors(NewAccountRequest newAccountRequest, BindingResult errors) {
        String password = newAccountRequest.getPassword();
        String confirmPassword = newAccountRequest.getConfirmPassword();
        String email = newAccountRequest.getEmail();
        LocalDate dateOfBirth = newAccountRequest.getDateOfBirth();

        if (email != null && accountRepository.existsByEmailIgnoreCase(email)) {
            errors.addError(new FieldError("newAccountRequest", "email", "Email is already registered"));
            return;
        }
        if (dateOfBirth == null) {
            return;
        }
        if (Period.between(dateOfBirth, LocalDate.now()).getYears() < MIN_AGE) {
            errors.addError(new FieldError("newAccountRequest", "dateOfBirth", "You must be " + MIN_AGE + " years or older"));
            return;
        }
        if (!password.equals(confirmPassword)) {
            errors.addError(new FieldError("newAccountRequest", "confirmPassword", "Passwords must be the same"));
        }
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
        accountRepository.enableAccountById(code.getAccount().getAccountId());
        codeService.deleteById(code.getCodeId());
    }

    @Transactional
    public void updateAccount(UpdateAccountRequest updateInfo, HashMap<String, String> message, AccountDetails accountDetails) {
        if (!accountDetails.email().equals(updateInfo.getEmail())) {
            // user changed the email in inspect element
            message.clear();
            message.put("updateStatus", "false");
            return;
        }
        // everything good from here, I hope
        Account account = accountDetails.account();
        account.setFirstName(updateInfo.getFirstName());
        account.setLastName(updateInfo.getLastName());
        account.setDateOfBirth(updateInfo.getDateOfBirth());

        message.clear();
        message.put("updateStatus", "true");
        accountRepository.save(account);
    }

    @Transactional
    public boolean delete(String email, AccountDetails accountDetails,
                          HttpServletRequest request, HttpServletResponse response) {
        if (!accountDetails.email().equals(email) || !accountRepository.existsByEmailIgnoreCase(email)) {
            // user changed the email in inspect element or the email is not in the database, for some weird reason
            return false;
        }
        accountRepository.deleteByEmailIgnoreCase(email);
        doManualLogout(request);
        return true;
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

    public void doManualLogout(HttpServletRequest request) {
        try {
            request.logout();
            request.getSession().invalidate();
            SecurityContextHolder.getContext().setAuthentication(null);
        }
        catch (ServletException e) {
            System.out.println("Logout failed in: doManualLogout(), Exception: " + e);
        }
    }

    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    @Transactional
    public void disableById(Long accountId, HashMap<String, String> message) {
        String email = findByAccountId(accountId).getEmail();
        accountRepository.disableAccountById(accountId);
        message.clear();
        message.put("adminDisableStatus", "true");
        message.put("adminDisableMessage", "Account id: " + accountId + ", with email: " + email + " <strong>has been disabled</strong>");
    }

    @Transactional
    public void enableById(Long accountId, HashMap<String, String> message) {
        String email = findByAccountId(accountId).getEmail();
        accountRepository.enableAccountById(accountId);
        message.clear();
        message.put("adminEnableStatus", "true");
        message.put("adminEnableMessage", "Account id: " + accountId + ", with email: " + email + " <strong>has been enabled</strong>");
    }

    @Transactional
    public void deleteById(Long accountId, HashMap<String, String> message) {
        String email = findByAccountId(accountId).getEmail();
        accountRepository.deleteById(accountId);
        message.clear();
        message.put("adminDeleteStatus", "true");
        message.put("adminDeleteMessage", "Account id: " + accountId + ", with email: " + email + " <strong>has been deleted</strong>");
    }


}
