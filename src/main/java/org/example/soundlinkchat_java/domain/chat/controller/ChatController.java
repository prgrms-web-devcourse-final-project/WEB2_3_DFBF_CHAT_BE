package org.example.soundlinkchat_java.domain.chat.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.soundlinkchat_java.domain.chat.dto.ChatDto;
import org.example.soundlinkchat_java.domain.chat.service.ChatService;
import org.example.soundlinkchat_java.global.annotation.ChatMessage;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @MessageMapping("/sendMessage")
    @ChatMessage // AOP 가 잡아주는 어노테이션입니당.
    public ChatDto sendMessage(Message<?> message, ChatDto incomingDto) {
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

        return chatService.addMessage(safeDto);
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
