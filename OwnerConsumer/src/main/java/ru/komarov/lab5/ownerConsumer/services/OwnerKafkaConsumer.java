package ru.komarov.lab5.ownerConsumer.services;

import lombok.SneakyThrows;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;
import ru.komarov.lab5.ownerConsumer.ChainOfResponsibility.*;

@Component
public class OwnerKafkaConsumer {
    private final HandlerBase handler;

    @Autowired
    public OwnerKafkaConsumer(OwnerService ownerService){
        handler = new AddOwnerHandler(ownerService);
        handler.addNext(new DeleteOwnerHandler(ownerService));
        handler.addNext(new FindAllHandler(ownerService));
        handler.addNext(new FindOwnerHandler(ownerService));
        handler.addNext(new UpdateOwnerHandler(ownerService));
    }

    @SneakyThrows
    @KafkaListener(topics = "owner-topic", groupId = "owner-group")
    @SendTo
    public Message<?> listen(ConsumerRecord<String, Object> consumerRecord){
        String key = consumerRecord.key();
        String value = RequestParser.getRequest(consumerRecord.value());

        String response = handler.handle(key, value);

        return MyMessageBuilder.createResponse(key, response);
    }
}
