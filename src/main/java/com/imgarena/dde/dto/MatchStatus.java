package com.imgarena.dde.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.OffsetDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MatchStatus  {

  private OffsetDateTime timestamp;
  private String umpireCountry;
  private String umpire;
  private String teamAPlayer1;
  private String teamAPlayer2;
  private String teamBPlayer1;
  private String teamBPlayer2;
  private TossChooser tossChooser;
  private MatchState matchState;
  private int numSets;
  private ScoringTypes scoringType;
  private TeamNames firstServer;
  private TeamNames tossWinner;
  private int courtNum;
  private PlayersDetails teamAPlayersDetails;
  private PlayersDetails teamBPlayersDetails;
  private String umpireCode;
  private TieBreakTypes tieBreakType;

  public String getUmpireCountry() {
    return umpireCountry;
  }

  public void setUmpireCountry(String umpireCountry) {
    this.umpireCountry = umpireCountry;
  }

  public String getUmpire() {
    return umpire;
  }

  public void setUmpire(String umpire) {
    this.umpire = umpire;
  }

  public String getTeamAPlayer1() {
    return teamAPlayer1;
  }

  public void setTeamAPlayer1(String teamAPlayer1) {
    this.teamAPlayer1 = teamAPlayer1;
  }

  public String getTeamAPlayer2() {
    return teamAPlayer2;
  }

  public void setTeamAPlayer2(String teamAPlayer2) {
    this.teamAPlayer2 = teamAPlayer2;
  }

  public String getTeamBPlayer1() {
    return teamBPlayer1;
  }

  public void setTeamBPlayer1(String teamBPlayer1) {
    this.teamBPlayer1 = teamBPlayer1;
  }

  public String getTeamBPlayer2() {
    return teamBPlayer2;
  }

  public void setTeamBPlayer2(String teamBPlayer2) {
    this.teamBPlayer2 = teamBPlayer2;
  }

  public TossChooser getTossChooser() {
    return tossChooser;
  }

  public void setTossChooser(TossChooser tossChooser) {
    this.tossChooser = tossChooser;
  }

  public MatchState getMatchState() {
    return matchState;
  }

  public void setMatchState(MatchState matchState) {
    this.matchState = matchState;
  }

  public int getNumSets() {
    return numSets;
  }

  public void setNumSets(int numSets) {
    this.numSets = numSets;
  }

  public ScoringTypes getScoringType() {
    return scoringType;
  }

  public void setScoringType(ScoringTypes scoringType) {
    this.scoringType = scoringType;
  }

  public TeamNames getFirstServer() {
    return firstServer;
  }

  public void setFirstServer(TeamNames firstServer) {
    this.firstServer = firstServer;
  }

  public TeamNames getTossWinner() {
    return tossWinner;
  }

  public void setTossWinner(TeamNames tossWinner) {
    this.tossWinner = tossWinner;
  }

  public int getCourtNum() {
    return courtNum;
  }

  public void setCourtNum(int courtNum) {
    this.courtNum = courtNum;
  }

  public PlayersDetails getTeamAPlayersDetails() {
    return teamAPlayersDetails;
  }

  public void setTeamAPlayersDetails(PlayersDetails teamAPlayersDetails) {
    this.teamAPlayersDetails = teamAPlayersDetails;
  }

  public PlayersDetails getTeamBPlayersDetails() {
    return teamBPlayersDetails;
  }

  public void setTeamBPlayersDetails(PlayersDetails teamBPlayersDetails) {
    this.teamBPlayersDetails = teamBPlayersDetails;
  }

  public String getUmpireCode() {
    return umpireCode;
  }

  public void setUmpireCode(String umpireCode) {
    this.umpireCode = umpireCode;
  }

  public TieBreakTypes getTieBreakType() {
    return tieBreakType;
  }

  public void setTieBreakType(TieBreakTypes tieBreakType) {
    this.tieBreakType = tieBreakType;
  }
}
