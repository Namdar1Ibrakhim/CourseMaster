package com.coursemaster.service.impl;

import com.coursemaster.dto.mail.MailStructureDto;
import com.coursemaster.service.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SmtpMailServiceImpl implements MailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromMail;

    @Override
    @Async
    public void sendMail(MailStructureDto mailStructure) {
        log.info("Preparing to send email to {}", mailStructure.getMail());

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromMail);
        message.setTo(mailStructure.getMail());
        message.setSubject(mailStructure.getSubject());
        message.setText(mailStructure.getMessage());

        try {
            mailSender.send(message);
            log.info("Email sent successfully to {}", mailStructure.getMail());
        } catch (Exception e) {
            log.error("Error sending email to {}: {}", mailStructure.getMail(), e.getMessage(), e);
            throw e;
        }
    }
}
