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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.HashMap;

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

    @Transactional
    public void updateAccount(UpdateAccountRequest updateInfo, HashMap<String, Boolean> message, AccountDetails accountDetails) {
        if (!accountDetails.email().equals(updateInfo.getEmail())) {
            // user changed the email in inspect element
            message.clear();
            message.put("updateStatus", false);
            return;
        }
        // everything good from here, I hope
        Account account = accountDetails.account();
        account.setFirstName(updateInfo.getFirstName());
        account.setLastName(updateInfo.getLastName());
        account.setDateOfBirth(updateInfo.getDateOfBirth());

        message.clear();
        message.put("updateStatus", true);
        accountRepository.save(account);
    }

    @Transactional
    public boolean delete(String email, AccountDetails accountDetails,
                          HttpServletRequest request, HttpServletResponse response) {
        if (!accountDetails.email().equals(email) || !accountRepository.existsByEmail(email)) {
            // user changed the email in inspect element or the email is not in the database, for some weird reason
            return false;
        }
        accountRepository.deleteByEmail(email);
        doManualLogout(request, response);
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

    private void doManualLogout(HttpServletRequest request, HttpServletResponse response) {
        // there is a 100% chance the try below could be reduced to 3 lines
        try {
            request.logout();
//            request.getSession(false);
//            SecurityContext context = SecurityContextHolder.getContext();
//            SecurityContextHolder.clearContext();
//            context.setAuthentication(null);
//            for (Cookie cookie : request.getCookies()) {
//                String cookieName = cookie.getName();
//                Cookie cookieToDelete = new Cookie(cookieName, null);
//                cookieToDelete.setMaxAge(0);
//                response.addCookie(cookieToDelete);
//            }
        }
        catch (ServletException e) {
            System.out.println("Logout after account deletion failed" + e);
        }
    }
}
