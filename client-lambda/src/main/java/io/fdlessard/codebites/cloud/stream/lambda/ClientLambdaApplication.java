package io.fdlessard.codebites.cloud.stream.lambda;

import java.util.Base64;
import java.util.List;
import java.util.function.Consumer;

import com.amazonaws.services.lambda.runtime.events.KafkaEvent;
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
  public Consumer<Message<KafkaEvent>> receiveKafkaEvent() {
    return message -> {
      logger.info("receiveKafkaEvent() - Message: {}", message);
      MessageHeaders messageHeaders = message.getHeaders();
      logger.info("receiveKafkaEvent() - MessageHeaders: {}", messageHeaders);
      KafkaEvent kafkaEvent = message.getPayload();
      logger.info("receiveKafkaEvent() - kafkaEvent: {}", kafkaEvent);
      List<KafkaEvent.KafkaEventRecord> kafkaEventRecords = kafkaEvent.getRecords().get("client-0");
      KafkaEvent.KafkaEventRecord kafkaEventRecord = kafkaEventRecords.get(0);
      logger.info("receiveKafkaEvent() - kafkaEventRecord: {}", kafkaEventRecord);
      String kafkaEventRecordValue = kafkaEventRecord.getValue();
      logger.info("receiveKafkaEvent() - kafkaEventRecordValue: {}", kafkaEventRecordValue);
      String decodedValue = decode(kafkaEventRecordValue);
      logger.info("receiveKafkaEvent() - decodedValue: {}", decodedValue);
    };
  }

  private static String decode(String s) {
    Base64.Decoder decoder = Base64.getDecoder();
    byte[] bytes = decoder.decode(s);

    return new String(bytes);
  }


}
