package org.example.soundlinkchat_java.domain.chat.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.soundlinkchat_java.domain.chat.dto.ChatDto;
import org.example.soundlinkchat_java.domain.chat.dto.ChatResponseDto;
import org.example.soundlinkchat_java.domain.chat.service.ChatService;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;


    @MessageMapping("/sendMessage")
    public ChatResponseDto sendMessage(Message<?> message, ChatDto incomingDto) {
        Map<String, Object> sessionAttrs =
                (Map<String, Object>) message.getHeaders().get("simpSessionAttributes");
        Long userId = (Long) sessionAttrs.get("userId");

        ChatDto safeDto = new ChatDto(
                incomingDto.chatRoomId(),
                userId,
                incomingDto.message(),
                null
        );
        log.info("[WebSocket] Received message: chatRoomId={}, userId={}", safeDto.chatRoomId(), userId);

        ChatDto savedDto = chatService.addMessage(safeDto);

        try {
            String msgJson = objectMapper.writeValueAsString(savedDto);
            kafkaTemplate.send("chat-topic", msgJson);
        } catch (Exception e) {
            log.error("Failed to send to Kafka", e);
        }

        return new ChatResponseDto(
                savedDto.chatRoomId(),
                savedDto.fromUserId(),
                savedDto.message(),
                savedDto.createdAt(),
                true
        );
    }

    @MessageMapping("/extendSession")
    public void extendSession(Message<?> message) {
        Map<String, Object> sessionAttrs = (Map<String, Object>) message.getHeaders().get("simpSessionAttributes");
        if (sessionAttrs == null) return;

        // 현재 만료 시점
        Long expireTime = (Long) sessionAttrs.get("expireTime");
        if (expireTime == null) return;

        // 10분 연장
        long extendMs = 10 * 60 * 1000L;
        long newExpireTime = expireTime + extendMs;

        sessionAttrs.put("expireTime", newExpireTime);
        log.info("[WebSocket] 10분 추가 연장 되었습니다. expireTime={}", newExpireTime);
    }
}
