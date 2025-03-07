package org.example.soundlinkchat_java.domain.chat.controller;

import lombok.RequiredArgsConstructor;
import org.example.soundlinkchat_java.domain.chat.dto.ChatResponseDto;
import org.example.soundlinkchat_java.domain.chat.service.ChatService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat")
@CrossOrigin(origins = "http://localhost:63342")
public class ChatRestController {
    private final ChatService chatService;

    @GetMapping("/history/{chatRoomId}")
    public ResponseEntity<List<ChatResponseDto>> getChatHistory(
            @PathVariable String chatRoomId,
            @AuthenticationPrincipal Long currentUserId
    ) {
        List<ChatResponseDto> chatHistory =
                chatService.getChatHistoryByRoomId(chatRoomId, currentUserId);

        if (chatHistory.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(chatHistory);
    }
}
