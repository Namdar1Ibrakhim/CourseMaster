package com.coursemaster.rabbit;

import com.coursemaster.config.RabbitMQConfig;
import com.coursemaster.dto.mail.MailStructureDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RabbitMailProducer {

    private final RabbitTemplate rabbitTemplate;

    public void sendMailMessage(MailStructureDto mailStructure) {
        log.info("Publishing email message to RabbitMQ for {}", mailStructure.getEmail());
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.ROUTING_KEY, mailStructure);
    }
}
