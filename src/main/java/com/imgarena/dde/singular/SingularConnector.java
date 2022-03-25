package com.imgarena.dde.singular;

import com.imgarena.dde.singular.dto.CreateAppInstanceRequest;
import com.imgarena.dde.singular.dto.LoadCompositionRequest;
import com.imgarena.dde.singular.dto.SingularAppInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class SingularConnector {
  private static final Logger LOG = LoggerFactory.getLogger(SingularConnector.class);

  private static final String FOLDER_ID = "16c27d34-4b61-4d55-9110-c8bd12af6786";
  private static final int APPTEMPLATE_ID = 57; // Singular Studio

  public static final Long COMPOSITION_ID = 426059L;
  public static final String SHOW_SCOREBUG_JSON = """
      [
          {
              "compositionName": "Score Bug - Tennis",
              "animation": {
                  "action": "play",
                  "to": "In"
              }
          }
      ]
      """;
  private static final String APPINSTANCES_ID = "/appinstances/{appId}";

  private final WebClient singularClient;

  public SingularConnector(WebClient singularClient) {
    this.singularClient = singularClient;
  }

  public SingularAppInstance createAppInstance(String name) {
    LOG.info("Creating Singular app instance...");
    return singularClient
        .post()
        .uri("/appinstances")
        .bodyValue(new CreateAppInstanceRequest(FOLDER_ID, APPTEMPLATE_ID, name))
        .exchangeToMono(
            response -> {
              LOG.info("Create app instance Response Status: {}", response.statusCode().getReasonPhrase());
              if (response.statusCode().equals(HttpStatus.OK)) {
                return response.bodyToMono(SingularAppInstance.class);
              } else {
                return Mono.empty();
              }
            })
        .block();
  }

  public void loadComposition(Long appId, Long compositionId) {
    LOG.info("Loading composition id {} in app id {}", compositionId, appId);
    singularClient
        .put()
        .uri(uriBuilder -> uriBuilder.path(APPINSTANCES_ID + "/composition").build(appId))
        .bodyValue(new LoadCompositionRequest(compositionId))
        .exchangeToMono(response -> {
          LOG.info("Loading composition Response Status: {}", response.statusCode().getReasonPhrase());
          return Mono.empty();
        })
        .block();
  }

  public Mono<String> updateShowData(Long appId, String data) {
    LOG.info("Updating Singular app show data...");
    return singularClient
        .put()
        .uri(uriBuilder -> uriBuilder.path(APPINSTANCES_ID + "/control").build(appId))
        .bodyValue(data)
        .exchangeToMono(
            response -> {
              LOG.info("Updating show data Response Status: {}", response.statusCode().getReasonPhrase());
              if (response.statusCode().equals(HttpStatus.OK)) {
                return response.bodyToMono(String.class);
              } else {
                // just return the error msg
                return Mono.just(response.statusCode().getReasonPhrase());
              }
            });
  }

  public void deleteAppInstance(Long appId) {
    LOG.info("Deleting Singular app instance {}", appId);
    singularClient
        .delete()
        .uri(uriBuilder -> uriBuilder.path(APPINSTANCES_ID).build(appId))
        .exchangeToMono(
            response -> {
              LOG.info("Deleting app instance Response Status: {}", response.statusCode().getReasonPhrase());
              if (response.statusCode().equals(HttpStatus.OK)) {
                return response.bodyToMono(String.class);
              } else {
                // just return the error msg
                return Mono.just(response.statusCode().getReasonPhrase());
              }
            })
        .subscribe();
  }
}
