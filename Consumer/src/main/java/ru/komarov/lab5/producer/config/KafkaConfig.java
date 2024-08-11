package ru.komarov.lab5.producer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;

import java.time.Duration;

@Configuration
@EnableKafka
public class KafkaConfig {
    private static final String CAT_REPLY_TOPICS = "cat-topic-reply";
    private static final String CAT_CONSUMER_GROUPS = "group-cat";
    private static final String OWNER_REPLY_TOPICS = "owner-topic-reply";
    private static final String OWNER_CONSUMER_GROUPS = "group-owner";

    @Bean
    public ReplyingKafkaTemplate<String, Object, Object> replyingCatTemplate(
            ProducerFactory<String, Object> pf,
            ConcurrentMessageListenerContainer<String, Object> repliesCatContainer) {
        ReplyingKafkaTemplate<String, Object, Object> replyTemplate = new ReplyingKafkaTemplate<>(pf, repliesCatContainer);
        replyTemplate.setDefaultReplyTimeout(Duration.ofSeconds(10));
        replyTemplate.setSharedReplyTopic(true);
        return replyTemplate;
    }

    @Bean
    public ConcurrentMessageListenerContainer<String, Object> repliesCatContainer(
            ConcurrentKafkaListenerContainerFactory<String, Object> containerFactory) {
        ConcurrentMessageListenerContainer<String, Object> repliesContainer = containerFactory.createContainer(CAT_REPLY_TOPICS);
        repliesContainer.getContainerProperties().setGroupId(CAT_CONSUMER_GROUPS);
        repliesContainer.setAutoStartup(false);
        return repliesContainer;
    }

    @Bean
    public ReplyingKafkaTemplate<String, Object, Object> replyingOwnerTemplate(
            ProducerFactory<String, Object> pf,
            ConcurrentMessageListenerContainer<String, Object> repliesOwnerContainer) {
        ReplyingKafkaTemplate<String, Object, Object> replyTemplate = new ReplyingKafkaTemplate<>(pf, repliesOwnerContainer);
        replyTemplate.setDefaultReplyTimeout(Duration.ofSeconds(10));
        replyTemplate.setSharedReplyTopic(true);
        return replyTemplate;
    }

    @Bean
    public ConcurrentMessageListenerContainer<String, Object> repliesOwnerContainer(
            ConcurrentKafkaListenerContainerFactory<String, Object> containerFactory) {
        ConcurrentMessageListenerContainer<String, Object> repliesContainer = containerFactory.createContainer(OWNER_REPLY_TOPICS);
        repliesContainer.getContainerProperties().setGroupId(OWNER_CONSUMER_GROUPS);
        repliesContainer.setAutoStartup(false);
        return repliesContainer;
    }
}
