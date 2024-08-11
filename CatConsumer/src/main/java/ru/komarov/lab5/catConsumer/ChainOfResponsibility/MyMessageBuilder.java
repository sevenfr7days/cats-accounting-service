package ru.komarov.lab5.catConsumer.ChainOfResponsibility;


import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
public class MyMessageBuilder {
    public static Message<String> createResponse(String key, String request) {
        return MessageBuilder
                .withPayload(request)
                .setHeader(KafkaHeaders.RECEIVED_KEY, key)
                .build();
    }
}
