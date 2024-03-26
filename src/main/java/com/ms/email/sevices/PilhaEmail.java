package com.ms.email.sevices;

import com.ms.email.dtos.EmailRecordDto;
import org.springframework.stereotype.Service;

import java.util.Stack;

@Service
public class PilhaEmail {

    private Stack<EmailRecordDto> emails = new Stack<>();

    public PilhaEmail() {
        this.emails = new Stack<>();
    }
    public void addEmail(EmailRecordDto emailRecordDto) {
        emails.push(emailRecordDto);
    }

    public EmailRecordDto removeEmail() {
        return emails.pop();
    }

    public Boolean isEmpty() {
        return emails.isEmpty();
    }
}
