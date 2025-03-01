package org.example.soundlinkchat_java.domain.chat.repositoty;

import org.example.soundlinkchat_java.domain.chat.dto.ChatDto;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ChatRepository extends MongoRepository<ChatDto, String> {
    @Query("{$or: [{'fromUserId': ?0, 'toUserId': ?1}, {'fromUserId': ?1, 'toUserId': ?0}]}")
    List<ChatDto> findByTwoUserIds(Long fromUserId, Long toUserId);
}
