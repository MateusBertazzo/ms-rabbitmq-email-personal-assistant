package com.ms.email.dtos;

import com.ms.email.models.entitys.EmailEntity;

public record EmailRecordDto(Long userId, String to, String subject, String text) {
}
