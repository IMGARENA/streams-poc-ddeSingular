package com.imgarena.dde;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.imgarena.dde.util.ResourceReader;
import com.jayway.jsonpath.JsonPath;
import java.io.IOException;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class JNodeTest {

  private static final Logger LOG = LoggerFactory.getLogger(JNodeTest.class);
  private static final ObjectMapper objectMapper = new ObjectMapper();
  private static final String PATH = "$.[?(@.compositionName == 'Score Bug - Tennis')].controlNode.payload.%s";

  @Test
  void test() throws IOException {

    var jsonString = ResourceReader.readFileToString("classpath:payload.json");
    JsonPath.parse(jsonString).set(PATH, "Jaime").json();
  }
}
