package com.ms.email.models.entitys;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "emails")
public class EmailEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "to_email")
    private String to;

    private String subject;

    private String text;

    public EmailEntity() {
    }

    public EmailEntity(Long userId, String to, String subject, String text) {
        this.userId = userId;
        this.to = to;
        this.subject = subject;
        this.text = text;
    }
}
