package com.example.sellthatthing.emailsender;

import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

@Service
@AllArgsConstructor
public class EmailSenderService {
    private final JavaMailSender mailSender;

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
            System.out.println("Email was not sent: " + e);
            throw new IllegalStateException("failed to send email");
        }
    }
}
