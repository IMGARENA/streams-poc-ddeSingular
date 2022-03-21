package com.imgarena.dde.data;

import com.imgarena.dde.dto.MatchStatusWrapper;
import com.imgarena.dde.dto.PointScore;
import com.jayway.jsonpath.JsonPath;
import java.time.OffsetDateTime;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SingularPayloadGenerator {

  private static final Logger LOG = LoggerFactory.getLogger(SingularPayloadGenerator.class);

  private static final String PATH_TO_PAYLOAD = "$.[?(@.compositionName == 'Score Bug - Tennis')].controlNode.payload.%s";
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

  private final OffsetDateTime scoringStartedAt;

  @Value("#{T(com.imgarena.dde.util.ResourceReader).readFileToString('classpath:payload.json')}")
  private String jsonFile;

  public SingularPayloadGenerator() {
    scoringStartedAt = OffsetDateTime.now().minusSeconds(30);
    LOG.info("Scoring started at {}", scoringStartedAt);
  }


  public String setNames(MatchStatusWrapper matchStatus) {
    LOG.info("Set names to {} - {}", matchStatus.getMatchStatus().getTeamAPlayer1(), matchStatus.getMatchStatus().getTeamBPlayer1());
    jsonFile =  JsonPath.parse(jsonFile)
        .set(PATH_TO_PAYLOAD.formatted(PLAYER_1_NAME), matchStatus.getMatchStatus().getTeamAPlayer1())
        .set(PATH_TO_PAYLOAD.formatted(PLAYER_2_NAME), matchStatus.getMatchStatus().getTeamBPlayer1())
        .jsonString();
    return jsonFile;
  }

  public String setScores(PointScore pointScore) {
    if (pointScore.getTimestamp().isBefore(scoringStartedAt)) {
      return StringUtils.EMPTY;
    }
    var serveTeam = pointScore.getServer().getTeam().getMarker();
    var serveTeamA = "1".equals(serveTeam);
    var serveTeamB = "2".equals(serveTeam);
    LOG.info("Set score to {} - {} Server {}", pointScore.getScore().currentGameScore().getPointsA(), pointScore.getScore().currentGameScore().getPointsB(), serveTeam);
    jsonFile =  JsonPath.parse(jsonFile)
        .set(PATH_TO_PAYLOAD.formatted(PLAYER_1_SCORE), pointScore.getScore().currentGameScore().getPointsA())
        .set(PATH_TO_PAYLOAD.formatted(PLAYER_2_SCORE), pointScore.getScore().currentGameScore().getPointsB())
        .set(PATH_TO_PAYLOAD.formatted(PLAYER_1_SET1), pointScore.getScore().currentSetScore().getGamesA())
        .set(PATH_TO_PAYLOAD.formatted(PLAYER_2_SET1), pointScore.getScore().currentSetScore().getGamesB())
        .set(PATH_TO_PAYLOAD.formatted(PLAYER_1_SERVE), serveTeamA)
        .set(PATH_TO_PAYLOAD.formatted(PLAYER_2_SERVE), serveTeamB)
        .jsonString();
    return jsonFile;
  }
}
