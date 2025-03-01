package org.example.soundlinkchat_java.domain.chat.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.soundlinkchat_java.domain.chat.dto.ChatDto;
import org.example.soundlinkchat_java.domain.chat.service.ChatService;
import org.example.soundlinkchat_java.global.annotation.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @MessageMapping("/sendMessage")
    @ChatMessage // AOP 가 잡아주는 어노테이션입니당.
    public ChatDto sendMessage(ChatDto chatDto) {
        log.info("[WebSocket Controller] Received message: {}", chatDto);

        return chatService.addMessage(chatDto);
    }
}
