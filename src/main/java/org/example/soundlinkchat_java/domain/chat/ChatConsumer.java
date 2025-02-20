package org.example.soundlinkchat_java.domain.chat;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ChatConsumer {

    @KafkaListener(topics = "chat-topic",
            groupId = "chat-consumer-group",
            properties = {
                    "spring.json.value.default.type=java.lang.String",
                    "auto.offset.reset=earliest"
            }
    )
    public void consumerChat(String message) {
        log.info("[좋은말 조아] Received message: {}", message);

        //메세지 처리 어케하지...
    }

    @KafkaListener(topics = "bad-word", groupId = "chat-consumer-group")
    public void consumerBadWord(String message) {
        log.info("[나쁜말 금지] Received message: {}", message);

        //메세지 처리 어케하지...
    }
}
