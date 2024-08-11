package ru.komarov.lab5.producer.service;

public interface KafkaProducer {
    Object kafkaRequestReply(String key, Object request);
}
