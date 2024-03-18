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
            // validando se email é valido
            validateEmail(emailRecordDto);

            // covertendo emailRecord para uma entidade
            EmailEntity emailEntity = convertToEntity(emailRecordDto);

            // enviando email ao usuario
            emailService.sendEmail(emailRecordDto);

            // salvando email no banco de dados
            emailRepository.save(emailEntity);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void validateEmail(EmailRecordDto emailRecord) {
        if (emailRecord == null) {
            throw new IllegalArgumentException("Dados de email não podem ser nulos");
        }

        if (!emailRecord.to().contains("@")) {
            throw new IllegalArgumentException("Email inválido");
        }
    }

    private EmailEntity convertToEntity(EmailRecordDto emailRecord) {
        EmailEntity emailEntity = new EmailEntity();

        BeanUtils.copyProperties(emailRecord, emailEntity);

        return emailEntity;
    }
}
