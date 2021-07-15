package io.fdlessard.codebites.cloud.stream.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

import java.util.function.Consumer;

@Slf4j
@SpringBootApplication
public class ClientKafkaListenerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClientKafkaListenerApplication.class, args);
    }

    @Bean
    Consumer<Message<Object>> receiveClient() {
        return message -> {
            logger.info("receiveClient() - Message: {}", message);
            MessageHeaders messageHeaders = message.getHeaders();
            logger.info("receiveClient() - MessageHeaders: {}", messageHeaders);
            Object receivedClient = message.getPayload();
            logger.info("receiveClient() - Payload: {}", receivedClient);

        };
    }

}
