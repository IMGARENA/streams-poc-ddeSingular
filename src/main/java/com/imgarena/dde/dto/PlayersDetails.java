package com.imgarena.dde.dto;

import org.apache.commons.lang3.StringUtils;

public record PlayersDetails(String player1Id, String player1Country, String player2Id, String player2Country) {

  private static final String UNKNOWN = "Unknown";

  public boolean isPLayersUnknown() {
    return StringUtils.isBlank(player1Id) || UNKNOWN.equals(player1Id);
  }
}
