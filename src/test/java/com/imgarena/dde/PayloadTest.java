package com.imgarena.dde;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.imgarena.dde.dto.payload.ControlNode;
import com.imgarena.dde.dto.payload.Payload;
import com.imgarena.dde.util.ResourceReader;
import com.jayway.jsonpath.JsonPath;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class PayloadTest {

  private static final Logger LOG = LoggerFactory.getLogger(PayloadTest.class);
  private static final ObjectMapper objectMapper = new ObjectMapper();

  private static final String PAYLOAD = """
      [
          {
              "compositionName" : "Score Bug - Tennis",
              "controlNode" : {
                  "payload" : {
                    "player1Name" : "Mafalda",
                    "player1Serve" : false,
                    "player1Score" : "15"
                  }
              }
          }
      ]
      """;

  @Test
  void test() throws IOException {

    Map<String, Object> payloadMap = Map.of("player1name", "Mafalda", "player1Score", "15", "player1Serve", false);
    var controlNode = new ControlNode(payloadMap);
    var payload = new Payload("Score Bug - Tennis", controlNode);
    var json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(List.of(payload));

    assertThat(json).isEqualTo(PAYLOAD);
  }
}
