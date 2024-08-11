package ru.komarov.lab5.catConsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class CatConsumerMain {
    public static void main(String[] args) {
        SpringApplication.run(CatConsumerMain.class, args);
    }
}