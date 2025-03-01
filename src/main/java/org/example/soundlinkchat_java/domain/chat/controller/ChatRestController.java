package org.example.soundlinkchat_java.domain.chat.controller;

import lombok.RequiredArgsConstructor;
import org.example.soundlinkchat_java.domain.chat.dto.ChatDto;
import org.example.soundlinkchat_java.domain.chat.service.ChatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat")
@CrossOrigin(origins = "http://localhost:63342")
public class ChatRestController {
    private final ChatService chatService;

    @GetMapping("/history/between")
    public ResponseEntity<List<ChatDto>> getChatHistory(
            @RequestParam("fromUserId") Long fromUserId,
            @RequestParam("toUserId") Long toUserId
    ) {
        List<ChatDto> chatHistory = chatService.getChatHistoryBetween(fromUserId, toUserId);
        return chatHistory != null && !chatHistory.isEmpty()
                ? ResponseEntity.ok(chatHistory)
                : ResponseEntity.noContent().build();
    }
}
