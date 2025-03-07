package org.example.soundlinkchat_java.domain.chat.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.soundlinkchat_java.domain.chat.dto.ChatDto;
import org.example.soundlinkchat_java.domain.chat.repositoty.ChatRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatRepository chatRepository;

    public List<ChatDto> getChatHistoryByRoomId(String chatRoomId) {
        return chatRepository.findByChatRoomId(chatRoomId);
    }

    // 2) 메시지 저장
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
