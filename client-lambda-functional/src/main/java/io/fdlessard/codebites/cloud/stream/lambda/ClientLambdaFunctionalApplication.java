package io.fdlessard.codebites.cloud.stream.lambda;

import com.amazonaws.services.lambda.runtime.events.KafkaEvent;
import com.amazonaws.services.lambda.runtime.events.KafkaEvent.KafkaEventRecord;
import java.util.Base64;
import java.util.List;
import java.util.function.Consumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.cloud.function.context.FunctionRegistration;
import org.springframework.cloud.function.context.FunctionType;
import org.springframework.cloud.function.context.FunctionalSpringApplication;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

@Slf4j
@SpringBootConfiguration
public class ClientLambdaFunctionalApplication implements
    ApplicationContextInitializer<GenericApplicationContext> {

  public static void main(String[] args) {
    FunctionalSpringApplication.run(ClientLambdaFunctionalApplication.class, args);
  }

  public Consumer<Message<KafkaEvent>> receiveKafkaEvent() {
    return message -> {
      logger.info("receiveKafkaEvent() - Message: {}", message);
      MessageHeaders messageHeaders = message.getHeaders();
      logger.info("receiveKafkaEvent() - MessageHeaders: {}", messageHeaders);
      KafkaEvent kafkaEvent = message.getPayload();
      logger.info("receiveKafkaEvent() - kafkaEvent: {}", kafkaEvent);
      List<KafkaEventRecord> kafkaEventRecords = kafkaEvent.getRecords().get("client-0");
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

  @Override
  public void initialize(GenericApplicationContext context) {
    context.registerBean("receiveKafkaEvent", FunctionRegistration.class,
        () -> new FunctionRegistration<>(receiveKafkaEvent())
            .type(FunctionType.consumer(Message.class)));
  }

}
