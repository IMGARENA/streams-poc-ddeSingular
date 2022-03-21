package com.imgarena.dde.dto;

public class MatchStatusWrapper implements DDEEvent {

  private MatchStatus matchStatus;

  public MatchStatus getMatchStatus() {
    return matchStatus;
  }

  public void setMatchStatus(MatchStatus matchStatus) {
    this.matchStatus = matchStatus;
  }
}
