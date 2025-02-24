package org.example.soundlinkchat_java.domain.chat.dto;

import java.sql.Timestamp;

public record ChatDto(
        Long userId,
        String message,
        Timestamp createdAt
) {
}
