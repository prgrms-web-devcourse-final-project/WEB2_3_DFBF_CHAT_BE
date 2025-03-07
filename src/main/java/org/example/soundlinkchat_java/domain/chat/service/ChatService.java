package org.example.soundlinkchat_java.domain.chat.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.soundlinkchat_java.domain.chat.dto.ChatDto;
import org.example.soundlinkchat_java.domain.chat.dto.ChatResponseDto;
import org.example.soundlinkchat_java.domain.chat.repositoty.ChatRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatRepository chatRepository;

    public List<ChatResponseDto> getChatHistoryByRoomId(String chatRoomId, Long currentUserId) {
        List<ChatDto> chatList = chatRepository.findByChatRoomId(chatRoomId);

        return chatList.stream()
                .map(chat -> new ChatResponseDto(
                        chat.chatRoomId(),
                        chat.fromUserId(),
                        chat.message(),
                        chat.createdAt(),
                        (chat.fromUserId() != null && chat.fromUserId().equals(currentUserId))
                ))
                .toList();
    }

    public ChatDto addMessage(ChatDto chatDto) {
        Date now = new Date();
        ChatDto dtoToSave = new ChatDto(
                chatDto.chatRoomId(),
                chatDto.fromUserId(),
                chatDto.message(),
                now
        );
        return chatRepository.save(dtoToSave);
    }

}
