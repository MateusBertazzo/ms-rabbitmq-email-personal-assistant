package com.ms.email.consumers;

import com.ms.email.dtos.EmailRecordDto;
import com.ms.email.models.entitys.EmailEntity;
import com.ms.email.models.repositorys.EmailRepository;
import com.ms.email.sevices.EmailServiceConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class EmailConsumer {

    final EmailServiceConfig emailService;

    final EmailRepository emailRepository;

    public EmailConsumer(EmailServiceConfig emailService, EmailRepository emailRepository) {
        this.emailService = emailService;
        this.emailRepository = emailRepository;
    }

    @RabbitListener(queues = "${broker.queue.email.name}")
    public void listenEmailQueue(@Payload EmailRecordDto emailRecordDto) {

        try {
            if (emailRecordDto == null) {
                throw new IllegalArgumentException("EmailRecordDto is null");
            }

            var emailEntity = new EmailEntity();
            BeanUtils.copyProperties(emailRecordDto, emailEntity);

            emailRepository.save(emailEntity);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
