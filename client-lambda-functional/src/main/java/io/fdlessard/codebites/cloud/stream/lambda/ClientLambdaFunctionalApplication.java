package io.fdlessard.codebites.cloud.stream.lambda;

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

  public Consumer<Message<Object>> receiveClient() {
    return message -> {
      logger.info("receiveClient() - Message: {}", message);
      MessageHeaders messageHeaders = message.getHeaders();
      logger.info("receiveClient() - MessageHeaders: {}", messageHeaders);
      Object receivedClient = message.getPayload();
      logger.info("receiveClient() - Payload: {}", receivedClient);
    };
  }

  @Override
  public void initialize(GenericApplicationContext context) {
    context.registerBean("receiveClient", FunctionRegistration.class,
        () -> new FunctionRegistration<>(receiveClient())
            .type(FunctionType.consumer(Message.class)));
  }

}
