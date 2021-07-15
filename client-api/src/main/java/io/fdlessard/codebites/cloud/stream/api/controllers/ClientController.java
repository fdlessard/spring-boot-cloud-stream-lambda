package io.fdlessard.codebites.cloud.stream.api.controllers;

import io.fdlessard.codebites.cloud.stream.api.model.Client;
import io.fdlessard.codebites.cloud.stream.api.services.ClientService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@AllArgsConstructor
@RestController
public class ClientController {

  private ClientService clientService;

  @PostMapping(value = "/clients")
  @ResponseStatus(HttpStatus.ACCEPTED)
  @ResponseBody
  public void post(@RequestBody Client client) {

    logger.info("ClientController.post(" + client + ")");
    clientService.publishClient(client);
  }


}
