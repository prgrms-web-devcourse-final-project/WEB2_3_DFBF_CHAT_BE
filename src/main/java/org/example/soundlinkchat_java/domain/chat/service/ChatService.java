package org.example.soundlinkchat_java.domain.chat.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.example.soundlinkchat_java.domain.chat.dto.ChatDto;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

@Slf4j
@Service
public class ChatService {
    private final List<ChatDto> chatMessages = Collections.synchronizedList(new ArrayList<>());

    public void addMessage(ChatDto chatDto) {
        chatMessages.add(chatDto);
    }

    public List<String> getChatHistory() {
        Properties props = new Properties();
        String KAFKA_BROKER = "localhost:9092";
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_BROKER);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "chat-history-consumer"); // 새 그룹 설정
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest"); // 🔥 과거 메시지 가져오기 위해 "earliest" 설정

        List<String> messages = new ArrayList<>();

        try (KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props)) {
            String CHAT_TOPIC = "chat-topic";
            consumer.subscribe(Collections.singletonList(CHAT_TOPIC));

            ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(5)); // 5초 동안 메시지 조회
            for (ConsumerRecord<String, String> record : records) {
                messages.add(record.value()); // 메시지 리스트에 추가
            }
        } catch (Exception e) {
            log.error("Kafka 메시지 조회 중 오류 발생", e);
        }

        return messages;
    }

}
