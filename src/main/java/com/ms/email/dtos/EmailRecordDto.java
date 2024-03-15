package com.ms.email.dtos;

public record EmailRecordDto(Long userId, String to, String subject, String text) {
}
