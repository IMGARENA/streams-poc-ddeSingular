package com.imgarena.dde.dto;

public enum TeamNames {
  // Tennis
  UnknownTeam("0"),
  TeamA("1"),
  TeamB("2"),

  // Badminton
  TeamAPlayer1("1"),
  TeamAPlayer2("1"),
  TeamBPlayer1("2"),
  TeamBPlayer2("2"),
  None("0"),
  Tie("0");

  private String marker;

  TeamNames(String s) {
    this.marker = s;
  }

  public String getMarker() {
    return marker;
  }
}
