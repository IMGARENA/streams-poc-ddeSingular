package com.imgarena.dde.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.imgarena.dde.data.SingularPayloadGenerator;
import com.imgarena.dde.dto.DDEEvent;
import com.imgarena.dde.dto.MatchStatusWrapper;
import com.imgarena.dde.dto.PointScore;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.client.WebSocketClient;
import reactor.core.publisher.Mono;

@Component
public class DDEConnector {

  private static final Logger LOG = LoggerFactory.getLogger(DDEConnector.class);

  private static final String DDE_URI = "wss://%s/events/%s/stream?&additionalPacketData=true&startPosition=1";
  private static final String AUTH_JSON = """
      {"authToken": "%s"}
      """;

  private final WebSocketClient client;

  private final WebClient singularClient;

  private final ObjectMapper objectMapper;

  private final SingularPayloadGenerator generator;

  @Value("${dde.api.host}")
  private String ddeHost;
  @Value("${dde.api.token}")
  private String ddeToken;


  public DDEConnector(WebSocketClient client, WebClient webClient, ObjectMapper objectMapper, SingularPayloadGenerator generator) {
    this.client = client;
    this.singularClient = webClient;
    this.objectMapper = objectMapper;
    this.generator = generator;
  }

  public void listen(String eventId) throws URISyntaxException {
    var authJson = AUTH_JSON.formatted(ddeToken);
    var uri = new URI(DDE_URI.formatted(ddeHost, eventId));
    LOG.info("Connecting to {}", uri);

    client
        .execute(
            uri,
            session ->
                session
                    .send(Mono.just(session.textMessage(authJson)))
                    .thenMany(session.receive()
                        .map(WebSocketMessage::getPayloadAsText).log()
                        .map(this::readPayload))
                        .filter(StringUtils::isNotBlank)
                        .delayElements(Duration.ofSeconds(2))
                        .flatMap(this::callSingular)
                        .then()
                    )
        .subscribe();
  }

  private String readPayload(String msgPayload)  {
    try {
      var event = objectMapper.readValue(msgPayload, DDEEvent.class);
      if (event instanceof MatchStatusWrapper matchStatus) {
        return generator.setNames(matchStatus);
      } else if (event instanceof PointScore pointScore) {
        return generator.setScores(pointScore);
      }

    } catch (JsonProcessingException e) {
      // swallow exception for now
      //LOG.warn("Error parsing JSON", e);
    }
    return StringUtils.EMPTY;
  }

  private Mono<String> callSingular(String s) {
    LOG.info("Calling Singular...");
    return singularClient.put().bodyValue(s)
        .exchangeToMono(response -> {
          LOG.info("Response Status: {}", response.statusCode().getReasonPhrase());
          if (response.statusCode().equals(HttpStatus.OK)) {
            return response.bodyToMono(String.class);
          }
          else {
            //just return the error msg
            return Mono.just(response.statusCode().getReasonPhrase());
          }
        });
  }
}
