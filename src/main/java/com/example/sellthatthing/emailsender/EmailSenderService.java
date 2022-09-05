package com.example.sellthatthing.emailsender;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

/**
 * @author Ife Sunmola
 * This class is used to send emails
 */
@Service
@AllArgsConstructor
@Async
public class EmailSenderService {
    private final JavaMailSender mailSender;
    private final Logger logger = LoggerFactory.getLogger(EmailSenderService.class);


    @Async
    public void sendMail(String subject, String fromEmail, String toEmail, String replyTo, String mailContent) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setSubject(subject);
            helper.setFrom(fromEmail, "SellYourThing");
            helper.setTo(toEmail);
            helper.setReplyTo(replyTo);
            helper.setText(mailContent, true);
            mailSender.send(mimeMessage);
        }
        catch (MessagingException | UnsupportedEncodingException e) {
            logger.warn("Email was not sent to " + toEmail + ". Exception message: " + e);
        }
    }
}
