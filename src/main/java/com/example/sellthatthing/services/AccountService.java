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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.HashMap;
import java.util.List;

import static com.example.sellthatthing.emailsender.MailBody.VERIFY_CODE_HTML;

/**
 * @author Ife Sunmola
 *
 * <p>Account Service class</p>
 * <p>Handles business logic for everything relating to users account</p>
 */
@Service
@AllArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final EmailSenderService emailSenderService;
    private final VerificationCodeService codeService;
    private static final int MIN_AGE = 16; // minimum required age to register
    private final Logger logger = LoggerFactory.getLogger(AccountService.class);


    /**
     * Finds an account by its id
     *
     * @param accountId The account id to find
     * @return The account object.
     * @throws ResourceNotFoundException if the account id was not found
     */
    public Account findByAccountId(Long accountId) {
        return accountRepository.findById(accountId).orElseThrow(()
                -> new ResourceNotFoundException("Account id: '" + accountId + "' was not found"));
    }

    /**
     * Finds an account by the users' email
     *
     * @param email The email to find
     * @return The account object.
     * @throws ResourceNotFoundException if the email was not found
     */
    public Account findByEmail(String email) {
        return accountRepository.findByEmailIgnoreCase(email).orElseThrow(()
                -> new ResourceNotFoundException("Email: '" + email + "' was not found"));
    }

    /**
     * This method checks if the Registration form has any errors. All the errors will be added to the binding result. They will be caught in the
     * RegistrationController class
     *
     * @param newAccountRequest an object containing the data from the registration form
     * @param errors            BindingResult used to store the errors gotten so thymeleaf can show it
     */
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

    /**
     * This method checks if the Update account form has any errors. All the errors will be added to the binding result. They will be caught in the
     * RegistrationController class
     *
     * @param updateInfo an object containing the data from the update form
     * @param errors     BindingResult used to store the errors gotten so thymeleaf can show it
     */
    public void checkForErrors(UpdateAccountRequest updateInfo, BindingResult errors) {

        LocalDate dateOfBirth = updateInfo.getDateOfBirth();
        if (dateOfBirth == null) {
            return;
        }
        if (Period.between(dateOfBirth, LocalDate.now()).getYears() < MIN_AGE) {
            errors.addError(new FieldError("updateAccountDto", "dateOfBirth", "You must be " + MIN_AGE + " years or older"));
        }
    }

    /**
     * This method is called by the RegistrationController to create a users account. A verification code is also sent to the users' email
     *
     * @param newAccountRequest An object containing the new users' information
     * @return VerificationDto an object containing the verification code id, the account id, and the users' password
     */
    @Transactional
    public VerificationDto createAccount(NewAccountRequest newAccountRequest) {
        String rawPassword = newAccountRequest.getPassword();

        newAccountRequest.setPassword(passwordEncoder.encode(newAccountRequest.getPassword()));

        Account newAccount = new Account(newAccountRequest); // create new account
        Long accountId = accountRepository.save(newAccount).getAccountId(); //save account to db and get the users id

        // send verification code
        VerificationCode code = new VerificationCode(newAccount); // create verification code
        Long codeId = codeService.save(code).getCodeId(); // save to db and get the code id
        String emailBody = VERIFY_CODE_HTML.replace("$firstName", newAccount.getFirstName())
                .replace("$verificationCode", code.getCode()); // replace with the users' first name and verification code

        // send the mail
        emailSenderService.sendMail(
                "SellYourThing: Activate your account",
                "donnotreply@sellyourthing.com",
                newAccount.getEmail(),
                "donotreply@sellyourthing.com",
                emailBody
        );
        logger.info("Verification code sent to: " + accountId + " is: " + code.getCode());
        return new VerificationDto(codeId, accountId, rawPassword);
    }

    /**
     * This method confirms the verification code and enables the users' account if the code is valid.
     * <p>If the code isn't valid, different pages will be returned along with error messages.</p>
     *
     * @param enteredVerificationCode the verification code that the user entered
     * @param accountId               the account id
     * @param message                 ModelAttribute used to send error/success messages to the view
     */
    @Transactional
    public String confirmVerificationCode(String enteredVerificationCode, Long verificationCodeId, Long accountId, HashMap<String, String> message) {
        VerificationCode code = codeService.findByCodeId(verificationCodeId);

        if (code.getExpiresAt().isBefore(LocalDateTime.now())) {// expired code. Delete the code and users' account. User is redirected to login page.
            message.put("expiredCode", "Verification code has expired. Register again.");
            codeService.deleteById(verificationCodeId);
            accountRepository.deleteById(accountId);
            return "redirect:/register";
        }

        if (!code.getCode().equals(enteredVerificationCode)) { // incorrect code
            message.put("incorrectCode", "Incorrect Verification Code.");
            return "verify-account";
        }

        // Valid code, update the confirmed at time, enable the users account, delete the code
        codeService.updateConfirmedAtById(code.getCodeId());// a bit redundant since the code will be deleted
        accountRepository.enableAccountById(code.getAccount().getAccountId());
        codeService.deleteById(code.getCodeId());
        return "success"; // will be used to auto login the user
    }

    /**
     * method to update the users account
     *
     * @param updateInfo     an object containing the account's new info
     * @param message        ModelAttribute used to send error/success messages to the view
     * @param accountDetails used for minor verifications
     */
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

    /**
     * method to delete the users account
     *
     * @param email          the user's email to delete
     * @param accountDetails used for verification
     * @param request        used to log out the user after account deletion
     * @return true if the account was deleted and false if not
     */
    @Transactional
    public boolean delete(String email, AccountDetails accountDetails, HttpServletRequest request) {
        if (!accountDetails.email().equals(email) || !accountRepository.existsByEmailIgnoreCase(email)) {
            // user changed the email in inspect element or the email is not in the database, for some weird reason
            return false;
        }
        accountRepository.deleteByEmailIgnoreCase(email);
        doManualLogout(request);
        return true;
    }

    /**
     * method to manually log the user in
     *
     * @param accountId   the account to log in
     * @param rawPassword the user's password
     * @param request     used to log the user in
     */
    public void doManualLogin(Long accountId, String rawPassword, HttpServletRequest request) {
        Account account = findByAccountId(accountId); // find the account
        if (passwordEncoder.matches(rawPassword, account.getPassword())) {// confirm that the raw password has not been tampered with
            try {
                request.login(account.getEmail(), rawPassword);// do the login
            }
            catch (ServletException e) {// some weird shit happened
                logger.warn("Login after verification failed. Exception given was: " + e);
            }
        }
        else {
            logger.warn("Account Id: " + account + " tried doing some magical voodoo and changed the HttpSession parameters.");
        }
    }

    /**
     * method to manually log the user out
     *
     * @param request used to log the user in
     */
    public void doManualLogout(HttpServletRequest request) {
        try {
            request.logout();
            request.getSession().invalidate();
            SecurityContextHolder.getContext().setAuthentication(null);
        }
        catch (ServletException e) {
            logger.warn("Log out failed. Exception given was: " + e);
        }
    }

    /**
     * @return A list of all the accounts
     */
    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    /**
     * disables a users account by id
     *
     * @param accountId the account id to disable
     * @param message   ModelAttribute used to send error/success messages to the view
     */
    @Transactional
    public void disableById(Long accountId, HashMap<String, String> message) {
        String email = findByAccountId(accountId).getEmail(); // to show success message
        accountRepository.disableAccountById(accountId);
        message.clear();
        message.put("adminDisableStatus", "true");
        message.put("adminDisableMessage", "Account id: " + accountId + ", with email: " + email + " <strong>has been disabled</strong>");
    }

    /**
     * enables a users account by id
     *
     * @param accountId the account id to enable
     * @param message   ModelAttribute used to send error/success messages to the view
     */
    @Transactional
    public void enableById(Long accountId, HashMap<String, String> message) {
        String email = findByAccountId(accountId).getEmail();
        accountRepository.enableAccountById(accountId);
        message.clear();
        message.put("adminEnableStatus", "true");
        message.put("adminEnableMessage", "Account id: " + accountId + ", with email: " + email + " <strong>has been enabled</strong>");
    }

    /**
     * deletes a users account by id
     *
     * @param accountId the account id to delete
     * @param message   ModelAttribute used to send error/success messages to the view
     */
    @Transactional
    public void deleteById(Long accountId, HashMap<String, String> message) {
        String email = findByAccountId(accountId).getEmail();
        accountRepository.deleteById(accountId);
        message.clear();
        message.put("adminDeleteStatus", "true");
        message.put("adminDeleteMessage", "Account id: " + accountId + ", with email: " + email + " <strong>has been deleted</strong>");
    }

}
