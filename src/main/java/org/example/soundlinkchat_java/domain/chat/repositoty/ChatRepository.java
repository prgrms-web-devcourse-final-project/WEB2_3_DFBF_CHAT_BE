package org.example.soundlinkchat_java.domain.chat.repositoty;

import org.example.soundlinkchat_java.domain.chat.dto.ChatDto;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ChatRepository extends MongoRepository<ChatDto, String> {
    List<ChatDto> findChatDtoByUserId(Long userId);
}
