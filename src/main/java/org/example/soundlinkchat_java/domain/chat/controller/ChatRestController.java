package org.example.soundlinkchat_java.domain.chat.controller;

import lombok.RequiredArgsConstructor;
import org.example.soundlinkchat_java.domain.chat.service.ChatService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat")
@CrossOrigin(origins = "http://localhost:63342")
public class ChatRestController {
    private final ChatService chatService;

    @GetMapping("/history")
    public List<String> getChatHistory() {
        return chatService.getChatHistory();
    }

}
