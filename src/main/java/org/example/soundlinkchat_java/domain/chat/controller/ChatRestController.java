package org.example.soundlinkchat_java.domain.chat.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
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
public class ChatRestController {
    private final ChatService chatService;

    @GetMapping("/history/{chatRoomId}")
    @Operation(summary = "채팅방 내 채팅 내역", description = "채팅 그 자체를 가져오는 API (배포서버에서만 작동)")
    public ResponseEntity<?> getChatHistory(
            @AuthenticationPrincipal Long userId,
            @PathVariable String chatRoomId
    ) {
        return chatService.getChatHistoryResponse(chatRoomId, userId);
    }

    @GetMapping("/dev/history")
    @Operation(summary = "채팅방 내 채팅 내역", description = "채팅 그 자체를 가져오는 API (8080포트에서만 작동합니다)")
    public ResponseEntity<?> getChatHistoryDev(
            @RequestParam("chatRoomId") String chatRoomId,
            @RequestParam("userId") Long userId
    ) { return chatService.getChatHistoryResponse(chatRoomId, userId); }
}
