package com.imgarena.dde;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.imgarena.dde.dto.PointScore;
import org.junit.jupiter.api.Test;

public class DateTimeTest {

  private final ObjectMapper objectMapper = JsonMapper.builder().findAndAddModules().build();

  String json = """
          {"server":{"team":"TeamA"},"timestamp":"2020-02-21T12:28:39.730Z","nextServer":{"team":"TeamA"},"score":{"currentSetScore":{"gamesA":0,"gamesB":0},"overallSetScore":{"setsA":2,"setsB":0},"currentGameScore":{"pointsA":"0","pointsB":"0","gameType":"StandardGame"},"previousSetsScore":[{"gamesA":6,"gamesB":3},{"gamesA":7,"gamesB":6,"tieBreakScore":{"pointsA":7,"pointsB":4}}]},"eventElementType":"PointScored","matchTime":"01:21:31","seqNum":393,"details":{"scoredBy":"TeamA","pointType":"Standard"}}
      """;

  @Test
  void test() throws JsonProcessingException {

    var pointScore = objectMapper.readValue(json, PointScore.class);

    assertThat(pointScore.getTimestamp()).isEqualTo("2020-02-21T12:28:39.730Z");
  }
}
