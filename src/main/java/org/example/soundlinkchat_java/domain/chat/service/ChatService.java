package org.example.soundlinkchat_java.domain.chat.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.soundlinkchat_java.domain.chat.dto.ChatDto;
import org.example.soundlinkchat_java.domain.chat.dto.ChatResponseDto;
import org.example.soundlinkchat_java.domain.chat.repositoty.ChatRepository;
import org.example.soundlinkchat_java.domain.user.service.UserService;
import org.example.soundlinkchat_java.global.auth.JwtProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatRepository chatRepository;
    private final JwtProvider jwtProvider;

    public ResponseEntity<?> getChatHistoryResponse(String chatRoomId, Long currentUserId) {
        List<ChatDto> chatList = chatRepository.findByChatRoomId(chatRoomId);

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

    public ChatDto addMessage(ChatDto chatDto) {
        ChatDto toSave = new ChatDto(
                chatDto.chatRoomId(),
                chatDto.fromUserId(),
                chatDto.message(),
                new Date()
        );
        return chatRepository.save(toSave);
    }

}
