package org.example.soundlinkchat_java.domain.chat.controller;

import org.example.soundlinkchat_java.domain.chat.dto.ChatDto;
import org.example.soundlinkchat_java.global.annotation.ChatMessage;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    @PostMapping("/send/message")
    @ChatMessage
    public ChatDto sendMessage(@RequestBody ChatDto message) {
        return message;
    }
}
