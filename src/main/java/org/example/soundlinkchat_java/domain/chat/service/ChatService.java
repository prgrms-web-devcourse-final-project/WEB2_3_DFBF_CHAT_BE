package org.example.soundlinkchat_java.domain.chat.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.soundlinkchat_java.domain.chat.dto.ChatDto;
import org.example.soundlinkchat_java.domain.chat.repositoty.ChatRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatRepository chatRepository;

    public void addMessage(ChatDto chatDto) {

        Date date = Date.from(Instant.now());
        ChatDto chatWithDate = new ChatDto(
                chatDto.userId(),
                chatDto.message(),
                date
        );
        chatRepository.save(chatWithDate);
    }

    public List<ChatDto> getChatHistory() {
        return chatRepository.findChatDtoByUserId(1L);
    }

}
