package org.example.soundlinkchat_java.domain.chat.dto;

import java.util.Date;

public record ChatDto(
        Long userId,
        String message,
        Date createdAt
) {
}
