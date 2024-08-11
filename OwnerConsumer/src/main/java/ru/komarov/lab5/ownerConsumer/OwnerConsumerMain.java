package ru.komarov.lab5.ownerConsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class OwnerConsumerMain {
    public static void main(String[] args) {
        SpringApplication.run(OwnerConsumerMain.class, args);
    }
}
