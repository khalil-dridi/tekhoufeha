package com.tekhoufeha.auth.service;


import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class EmailService {


    private final JavaMailSender mailSender;



    public void sendVerificationEmail(
            String email,
            String token) {


        String verificationUrl =
                "http://localhost:8081/api/auth/verify-email?token="
                        + token;


        SimpleMailMessage message =
                new SimpleMailMessage();


        message.setTo(email);
        message.setSubject("Verify your TekhouFeha account");

        message.setText(
                "Welcome to TekhouFeha!\n\n"
                        + "Please verify your email by clicking this link:\n"
                        + verificationUrl
                        + "\n\nThis link expires in 24 hours."
        );


        mailSender.send(message);
    }
}