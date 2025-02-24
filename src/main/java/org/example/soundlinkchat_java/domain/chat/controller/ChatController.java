package org.example.soundlinkchat_java.domain.chat.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.soundlinkchat_java.domain.chat.dto.ChatDto;
import org.example.soundlinkchat_java.domain.chat.service.ChatService;
import org.example.soundlinkchat_java.global.annotation.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @MessageMapping("/sendMessage")
    @SendTo("/topic/public")
    @ChatMessage
    public ChatDto sendMessage(ChatDto chatDto) {
        log.info("[WebSocket Controller] Received message: {}", chatDto);

        ChatDto result = new ChatDto(
                chatDto.userId(),
                chatDto.message(),
                Timestamp.valueOf(LocalDateTime.now())
        );

        chatService.addMessage(result);

        return result;
    }
}
