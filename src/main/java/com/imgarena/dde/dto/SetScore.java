package com.imgarena.dde.dto;

public class SetScore {

  private int gamesA;
  private int gamesB;
  private TieBreakScore tieBreakScore;

  public int getGamesA() {
    return gamesA;
  }

  public void setGamesA(int gamesA) {
    this.gamesA = gamesA;
  }

  public int getGamesB() {
    return gamesB;
  }

  public void setGamesB(int gamesB) {
    this.gamesB = gamesB;
  }

  public TieBreakScore getTieBreakScore() {
    return tieBreakScore;
  }

  public void setTieBreakScore(TieBreakScore tieBreakScore) {
    this.tieBreakScore = tieBreakScore;
  }
}
