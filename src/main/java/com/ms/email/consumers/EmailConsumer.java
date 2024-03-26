package com.ms.email.consumers;

import com.ms.email.dtos.EmailRecordDto;
import com.ms.email.models.entitys.EmailEntity;
import com.ms.email.models.repositorys.EmailRepository;
import com.ms.email.sevices.EmailServiceConfig;
import com.ms.email.sevices.PilhaEmail;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class EmailConsumer {

    final EmailServiceConfig emailService;

    final EmailRepository emailRepository;

    final PilhaEmail pilhaEmail;

    public EmailConsumer(EmailServiceConfig emailService, EmailRepository emailRepository, PilhaEmail pilhaEmail) {
        this.emailService = emailService;
        this.emailRepository = emailRepository;
        this.pilhaEmail = pilhaEmail;
    }

    @RabbitListener(queues = "${broker.queue.email.name}")
    public void listenEmailQueue(@Payload EmailRecordDto emailRecordDto) {

        try {

            validateEmail(emailRecordDto);

            // adiciono objeto email a pilha de envio de emails
            pilhaEmail.addEmail(emailRecordDto);


            // envio email de acordo com a pilha
            startSendEmails();

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

    private void startSendEmails() {

        // só starto uma nova thread se não houver outra do mesmo nome em execução
        if (!isExecutingThreadSend()) {
            new Thread(() ->
                {
                    while (!pilhaEmail.isEmpty())
                        {
                            // adiciono email a uma variavel antes de remove-lo da pilha
                            EmailRecordDto emailForSend = pilhaEmail.removeEmail();

                            emailService.sendEmail(emailForSend);
                        }
                }, "Envio de emails").start();
        }
    }

    private boolean isExecutingThreadSend() {
        for (Thread thread : Thread.getAllStackTraces().keySet()) {
            if (thread.getName().equals("Envio de emails")) {
                return true;
            }
        }
        return false;
    }
}
