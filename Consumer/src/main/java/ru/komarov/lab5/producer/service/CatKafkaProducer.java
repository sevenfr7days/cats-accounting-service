package ru.komarov.lab5.producer.service;

import lombok.SneakyThrows;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class CatKafkaProducer implements KafkaProducer {
    private ReplyingKafkaTemplate<String, Object, Object> catReplyingTemplate;

    @Autowired
    public void setCatReplyingTemplate(@Qualifier("replyingCatTemplate") ReplyingKafkaTemplate<String, Object, Object> catReplyingTemplate) {
        this.catReplyingTemplate = catReplyingTemplate;
    }

    private String SEND_TOPICS = "cat-topic";

    @SneakyThrows
    @Override
    public Object kafkaRequestReply(String key, Object request) {
        ProducerRecord<String, Object> record = new ProducerRecord<>(SEND_TOPICS, key, request);
        RequestReplyFuture<String, Object, Object> replyFuture = catReplyingTemplate.sendAndReceive(record);
        SendResult<String, Object> sendResult = replyFuture.getSendFuture()
                .get(10, TimeUnit.SECONDS);

        ConsumerRecord<String, Object> consumerRecord = replyFuture.get(10, TimeUnit.SECONDS);

        return consumerRecord.value();
    }
}
