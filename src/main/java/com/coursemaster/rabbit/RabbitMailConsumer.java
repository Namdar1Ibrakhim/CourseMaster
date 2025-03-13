package com.coursemaster.rabbit;

import com.coursemaster.dto.mail.MailStructureDto;
import com.coursemaster.service.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RabbitMailConsumer {

    private final MailService mailService;
    private final RabbitMailProducer rabbitMailProducer;

    @RabbitListener(queues = "#{@mailQueue.name}")
    public void handleMailMessage(MailStructureDto mailStructure) {
        log.info("Received email message for {}", mailStructure.getEmail());
        try {
            mailService.sendMail(mailStructure);
            log.info("Email sent successfully to {}", mailStructure.getEmail());
        } catch (Exception e) {
            log.error("Error sending email to {}: {}", mailStructure.getEmail(), e.getMessage(), e);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            rabbitMailProducer.sendMailMessage(mailStructure);
            log.info("Requeued email message for {}", mailStructure.getEmail());
        }
    }
}
