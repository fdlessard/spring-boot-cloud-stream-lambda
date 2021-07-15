package io.fdlessard.codebites.cloud.stream.api.services;

import io.fdlessard.codebites.cloud.stream.api.model.Client;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ClientService {

    public static final String CLIENT_BINDING_NAME = "client-out-0";

    private StreamBridge streamBridge;

    private boolean publishingActivated;

    public ClientService(
            StreamBridge streamBridge,
            @Value("${client.publishingActivated}") boolean publishingActivated
    ) {
        this.streamBridge = streamBridge;
        this.publishingActivated = publishingActivated;
    }

    public void publishClient(Client client) {

        if (!publishingActivated) {
            logger.info("Publishing of client not activated - not sending create");
            return;
        }

        logger.info("ClientService.publishClient - {}", client);

        Message<Client> clientMessage = MessageBuilder.withPayload(client)
                .setHeader("type", "Client")
                .setHeader("action", "Create")
                .build();

        streamBridge.send(CLIENT_BINDING_NAME, client);
    }
}
