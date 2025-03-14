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
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromMail);
        message.setTo(mailStructure.getEmail());
        message.setSubject(mailStructure.getSubject());
        message.setText(mailStructure.getMessage());

        try {
            mailSender.send(message);
            log.info("Email sent successfully to {}", mailStructure.getEmail());
        } catch (Exception e) {
            log.error("Error sending email to {}: {}", mailStructure.getEmail(), e.getMessage(), e);
            throw e;
        }
    }
}
