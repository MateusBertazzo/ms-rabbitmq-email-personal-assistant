package com.ms.email.models.entitys;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "emails")
public class EmailEntity extends BaseEntity {
    private Long userId;

    private String to;

    private String subject;

    private String text;

    private Boolean sent;

    public EmailEntity() {
    }

    public EmailEntity(Long userId, String to, String subject, String text) {
        this.userId = userId;
        this.to = to;
        this.subject = subject;
        this.text = text;
        this.sent = false;
    }
}
