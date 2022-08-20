package com.example.sellthatthing.email;

import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@AllArgsConstructor
public class EmailSenderService {
    private final JavaMailSender mailSender;

    @Async
    public void send(String to, String email) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText(email, true);
            helper.setTo(to);
            helper.setSubject("Activate your account");
            helper.setFrom("donnotreply@sellyourthing.com");
            mailSender.send(mimeMessage);
        }
        catch (MessagingException e) {
            System.out.println("Email was not sent: " + e);
            throw new IllegalStateException("failed to send email");
        }
    }
}
