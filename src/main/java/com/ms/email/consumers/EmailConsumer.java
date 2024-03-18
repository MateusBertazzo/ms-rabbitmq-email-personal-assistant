package com.ms.email.consumers;

import com.ms.email.dtos.EmailRecordDto;
import com.ms.email.sevices.EmailServiceConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class EmailConsumer {

    final EmailServiceConfig emailService;

    public EmailConsumer(EmailServiceConfig emailService) {
        this.emailService = emailService;
    }

    @RabbitListener(queues = "${broker.queue.email.name}")
    public void listenEmailQueue(@Payload EmailRecordDto emailRecordDto) {
        emailService.sendEmail(emailRecordDto);
    }
}
