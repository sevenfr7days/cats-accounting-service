package ru.komarov.lab5.catConsumer.services;

import lombok.SneakyThrows;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;
import ru.komarov.lab5.catConsumer.ChainOfResponsibility.*;

@Component
public class CatKafkaConsumer {
    private final HandlerBase handler;

    @Autowired
    public CatKafkaConsumer(CatService catService){
        handler = new AddCatHandler(catService);
        handler.addNext(new AddFriendshipHandler(catService));
        handler.addNext(new DeleteCatHandler(catService));
        handler.addNext(new FindAllCatsHandler(catService));
        handler.addNext(new FindCatHandler(catService));
        handler.addNext(new FindCatsByBreedHandler(catService));
        handler.addNext(new FindCatsByColorHandler(catService));
        handler.addNext(new FindCatsByOwnerIdHandler(catService));
        handler.addNext(new FindFriendsHandler(catService));
        handler.addNext(new UpdateCatHandler(catService));
    }

    @SneakyThrows
    @KafkaListener(topics = "cat-topic", groupId = "cat-group")
    @SendTo
    public Message<?> listen(ConsumerRecord<String, Object> consumerRecord){
        String key = consumerRecord.key();
        String value = RequestParser.getRequest(consumerRecord.value());

        String response = handler.handle(key, value);

        return MyMessageBuilder.createResponse(key, response);
    }
}
