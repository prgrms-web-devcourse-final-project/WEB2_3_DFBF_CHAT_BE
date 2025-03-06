package org.example.soundlinkchat_java.domain.chat;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.soundlinkchat_java.domain.chat.dto.ChatDto;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ChatConsumer {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "chat-topic",
            groupId = "chat-consumer-group",
            properties = {
                    "spring.json.value.default.type=java.lang.String",
                    "auto.offset.reset=earliest"
            }
    )
    public void consumerChat(String message) {
        try {
            ChatDto dto = objectMapper.readValue(message, ChatDto.class);
            if (dto.chatRoomId() != null) {
                simpMessagingTemplate.convertAndSend("/queue/chat-" + dto.chatRoomId(), dto);
            }
        } catch (Exception e) {
            log.error("[ChatConsumer] Failed: {}", e.getMessage());
        }
    }

    @KafkaListener(topics = "bad-word", groupId = "chat-consumer-group")
    public void consumerBadWord(String message) {
        log.info("[나쁜말 금지] Received message: {}", message);

        simpMessagingTemplate.convertAndSend("/topic/badword", message);
    }
}
