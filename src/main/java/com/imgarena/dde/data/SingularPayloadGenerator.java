package com.imgarena.dde.data;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.imgarena.dde.dto.MatchStatusWrapper;
import com.imgarena.dde.dto.PointScore;
import com.imgarena.dde.dto.payload.ControlNode;
import com.imgarena.dde.dto.payload.Payload;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SingularPayloadGenerator {

  private static final Logger LOG = LoggerFactory.getLogger(SingularPayloadGenerator.class);

  private static final String PLAYER_1_NAME = "player1Name";
  private static final String PLAYER_1_SCORE = "player1Score";
  private static final String PLAYER_1_SERVE = "player1Serve";
  private static final String PLAYER_1_SET1 = "player1Set1";
  private static final String PLAYER_1_SET2 = "player1Set2";
  private static final String PLAYER_1_SET3 = "player1Set3";
  private static final String PLAYER_2_NAME = "player2Name";
  private static final String PLAYER_2_SCORE = "player2Score";
  private static final String PLAYER_2_SERVE = "player2Serve";
  private static final String PLAYER_2_SET1 = "player2Set1";
  private static final String PLAYER_2_SET2 = "player2Set2";
  private static final String PLAYER_2_SET3 = "player2Set3";
  private static final String COMPOSITION_NAME = "Score Bug - Tennis";
  private final OffsetDateTime scoringStartedAt;
  private final ObjectMapper objectMapper;

  public SingularPayloadGenerator(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
    scoringStartedAt = OffsetDateTime.now().minusSeconds(30);
    LOG.info("Scoring started at {}", scoringStartedAt);
  }

  public String setNames(MatchStatusWrapper matchStatus) throws JsonProcessingException {
    LOG.info(
        "Set names to {} - {}",
        matchStatus.getMatchStatus().getTeamAPlayer1(),
        matchStatus.getMatchStatus().getTeamBPlayer1());
    var payload =
        new Payload(COMPOSITION_NAME, new ControlNode(
                Map.of(
                    PLAYER_1_NAME, matchStatus.getMatchStatus().getTeamAPlayer1(),
                    PLAYER_2_NAME, matchStatus.getMatchStatus().getTeamBPlayer1())));
    return objectMapper.writeValueAsString(List.of(payload));
  }

  public String setScores(PointScore pointScore) throws JsonProcessingException {
    if (pointScore.getTimestamp().isBefore(scoringStartedAt)) {
      // return StringUtils.EMPTY;
    }
    var serveTeam = pointScore.getServer().getTeam().getMarker();
    var serveTeamA = "1".equals(serveTeam);
    var serveTeamB = "2".equals(serveTeam);
    LOG.info(
        "Set score to {} - {} Server {}",
        pointScore.getScore().currentGameScore().getPointsA(),
        pointScore.getScore().currentGameScore().getPointsB(),
        serveTeam);
    var payload =
        new Payload(COMPOSITION_NAME, new ControlNode(
                Map.of(
                    PLAYER_1_SCORE, pointScore.getScore().currentGameScore().getPointsA(),
                    PLAYER_2_SCORE, pointScore.getScore().currentGameScore().getPointsB(),
                    PLAYER_1_SET1, pointScore.getScore().currentSetScore().getGamesA(),
                    PLAYER_2_SET1, pointScore.getScore().currentSetScore().getGamesB(),
                    PLAYER_1_SERVE, serveTeamA,
                    PLAYER_2_SERVE, serveTeamB)));
    return objectMapper.writeValueAsString(List.of(payload));
  }
}
