package com.imgarena.dde.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.ArrayUtils;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ScoreSummary(
    GameScore currentGameScore,
    SetScore currentSetScore,
    OverallSetScore overallSetScore,
    List<SetScore> previousSetsScore) {


  public Integer getSetNumber() {
    if (overallSetScore != null) {
      return overallSetScore.getSetsA()
             + overallSetScore.getSetsB()
             + isInProgress(currentGameScore, currentSetScore);
    } else {
      return previousSetsScore == null
          ? 1
          : previousSetsScore.size() + isInProgress(currentGameScore, currentSetScore);
    }
  }

  private int isInProgress(GameScore currentGameScore, SetScore currentSetScore) {
    if ("0".equals(currentGameScore.getPointsA()) && "0".equals(currentGameScore.getPointsB())) {
      if (currentSetScore.getGamesA() == 0 && currentSetScore.getGamesB() == 0) {
        return 0;
      } else {
        return 1;
      }
    } else {
      return 1;
    }
  }

  public Integer[] getSetsScoreAsInteger(TeamNames team) {
    List<Integer> points = new ArrayList<>();
    for (SetScore setScore : previousSetsScore) {
      points.add(team == TeamNames.TeamA ? setScore.getGamesA() : setScore.getGamesB());
    }
    if (isInProgress(currentGameScore, currentSetScore) == 1) {
      points.add(
          team == TeamNames.TeamA ? currentSetScore.getGamesA() : currentSetScore.getGamesB());
    }
    return points.toArray(new Integer[]{});
  }

  public int[] getTieBreakScoreAsInt(TeamNames team) {
    List<Integer> points = new ArrayList<>();
    for (SetScore setScore : previousSetsScore) {
      TieBreakScore breakScore = setScore.getTieBreakScore();
      if (breakScore != null) {
        points.add(team == TeamNames.TeamA ? breakScore.getPointsA() : breakScore.getPointsB());
      } else {
        points.add(0);
      }
    }
    if (currentSetScore.getTieBreakScore() != null) {
      points.add(
          team == TeamNames.TeamA
              ? currentSetScore.getTieBreakScore().getPointsA()
              : currentSetScore.getTieBreakScore().getPointsB());
    }

    return ArrayUtils.toPrimitive(points.toArray(new Integer[]{}));
  }
}
