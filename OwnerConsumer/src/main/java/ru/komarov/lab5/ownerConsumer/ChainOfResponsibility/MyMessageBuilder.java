package ru.komarov.lab5.ownerConsumer.ChainOfResponsibility;

import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

public class MyMessageBuilder {
    public static Message<String> createResponse(String key, String request) {
        return MessageBuilder
                .withPayload(request)
                .setHeader(KafkaHeaders.KEY, key)
                .build();
    }
}
