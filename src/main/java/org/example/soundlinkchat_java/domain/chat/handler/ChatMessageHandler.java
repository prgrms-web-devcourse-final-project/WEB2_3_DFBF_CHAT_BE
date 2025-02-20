package org.example.soundlinkchat_java.domain.chat.handler;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.example.soundlinkchat_java.global.annotation.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ChatMessageHandler {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @AfterReturning(pointcut = "@annotation(chatMessage)", returning = "returnValue")
    public void sendToKafka(JoinPoint joinPoint, ChatMessage chatMessage, Object returnValue) {
        kafkaTemplate.send("chat-topic", returnValue.toString());
    }
}
