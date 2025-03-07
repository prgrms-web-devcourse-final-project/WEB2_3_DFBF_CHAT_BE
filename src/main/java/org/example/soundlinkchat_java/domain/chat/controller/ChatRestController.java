package org.example.soundlinkchat_java.domain.chat.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.soundlinkchat_java.domain.chat.dto.ChatDto;
import org.example.soundlinkchat_java.domain.chat.dto.ChatResponseDto;
import org.example.soundlinkchat_java.domain.chat.service.ChatService;
import org.example.soundlinkchat_java.global.auth.JwtProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat")
@CrossOrigin(origins = "http://localhost:63342")
public class ChatRestController {
    private final ChatService chatService;
    private final JwtProvider jwtProvider;

    @GetMapping("/history/{chatRoomId}")
    public ResponseEntity<List<ChatResponseDto>> getChatHistory(
            @PathVariable String chatRoomId,
            HttpServletRequest request
    ) {
        String token = jwtProvider.resolveAccessToken(request);
        if (token == null || !jwtProvider.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Long currentUserId = jwtProvider.getUserId(token);

        List<ChatDto> chatList = chatService.getChatHistoryByRoomId(chatRoomId);

        List<ChatResponseDto> chatHistory = chatList.stream()
                .map(chat -> {
                    boolean isMine = (chat.fromUserId() != null && chat.fromUserId().equals(currentUserId));
                    return new ChatResponseDto(
                            chat.chatRoomId(),
                            chat.fromUserId(),
                            chat.message(),
                            chat.createdAt(),
                            isMine
                    );
                })
                .toList();

        if (chatHistory.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(chatHistory);
    }
}

// 8081 -> 8081 포트 데이터 말고는 다 차단 (CORS)
// @CrossOrigin(origins = "http://localhost:63342") -> localhost:63342에서만 접근 가능
