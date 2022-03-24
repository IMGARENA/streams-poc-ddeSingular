package com.imgarena.dde.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient;
import org.springframework.web.reactive.socket.client.WebSocketClient;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.medialive.MediaLiveAsyncClient;
import software.amazon.awssdk.services.medialive.MediaLiveClient;

@Configuration
public class AppConfig {

  private static final String PROFILE_NAME = "sandbox";

  @Value("${singular.api.url}")
  private String singularUrl;

  @Value("${singular.username}")
  private String singularUsername;

  @Value("${singular.password}")
  private String singularPassword;

  @Bean
  public WebClient getWebClient() {
    return WebClient.builder()
        .baseUrl(singularUrl)
        .defaultHeaders(header -> header.setBasicAuth(singularUsername, singularPassword))
        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .build();
  }

  @Bean
  public WebSocketClient getWebSocketClient() {
    return new ReactorNettyWebSocketClient();
  }

  @Bean
  public ObjectMapper getObjectMapper() {
    return JsonMapper.builder().findAndAddModules().build();
  }


  @Bean
  MediaLiveClient mediaLive() {
    return MediaLiveClient.builder()
        .credentialsProvider(ProfileCredentialsProvider.create(PROFILE_NAME))
        .region(Region.EU_WEST_1)
        .build();
  }

  @Bean
  MediaLiveAsyncClient mediaLiveAsync() {
    return MediaLiveAsyncClient.builder()
        .credentialsProvider(ProfileCredentialsProvider.create(PROFILE_NAME))
        .region(Region.EU_WEST_1)
        .build();
  }
}
