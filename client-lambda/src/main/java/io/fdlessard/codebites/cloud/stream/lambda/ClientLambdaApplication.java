package io.fdlessard.codebites.cloud.stream.lambda;

import java.util.function.Consumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

@Slf4j
@SpringBootApplication
public class ClientLambdaApplication {

  public static void main(String[] args) {
    SpringApplication.run(ClientLambdaApplication.class, args);
  }

  @Bean
  public Consumer<Message<Object>> receiveClient() {
    return message -> {
      logger.info("receiveClient() - Message: {}", message);
      MessageHeaders messageHeaders = message.getHeaders();
      logger.info("receiveClient() - MessageHeaders: {}", messageHeaders);
      Object receivedClient = message.getPayload();
      logger.info("receiveClient() - Payload: {}", receivedClient);
    };
  }

}
