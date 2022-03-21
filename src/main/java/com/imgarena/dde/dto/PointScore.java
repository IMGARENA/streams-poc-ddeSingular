package com.imgarena.dde.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PointScore implements DDEEvent {

  @JsonFormat(pattern = "HH:mm:ss")
  private Date matchTime;

  //@JsonFormat(pattern = DateTimeFormatter.ISO_OFFSET_DATE_TIME)
  private OffsetDateTime timestamp;

  private Server server;
  private Server nextServer;
  private ScoreSummary score;

  public Date getMatchTime() {
    return matchTime;
  }

  public void setMatchTime(Date matchTime) {
    this.matchTime = matchTime;
  }

  public OffsetDateTime getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(OffsetDateTime timestamp) {
    this.timestamp = timestamp;
  }

  public Server getNextServer() {
    return nextServer;
  }

  public void setNextServer(Server nextServer) {
    this.nextServer = nextServer;
  }

  public ScoreSummary getScore() {
    return score;
  }

  public void setScore(ScoreSummary score) {
    this.score = score;
  }

  public Server getServer() {
    return server;
  }

  public void setServer(Server server) {
    this.server = server;
  }

  public String getNextServerNumber() {
    return TeamNames.TeamA.equals(nextServer.getTeam()) ? "1" : "2";
  }

  public String getServerName() {
    return TeamNames.TeamA.equals(server.getTeam()) ? "A" : "B";
  }
}
