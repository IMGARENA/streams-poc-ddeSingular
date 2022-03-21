package com.imgarena.dde.data;

import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("payload")
public class SingularTennisPayload {
  String player1Name ="";
  String player1Score = "0";
  String player1Serve = "false";
  String player1Set1 = "0";
  String player1Set2 = "0";
  String player1Set3 = "0";
  String player2Name = "";
  String player2Score = "0";
  String player2Serve = "false";
  String player2Set1 = "0";
  String player2Set2 = "0";
  String player2Set3 = "0";

  public String getPlayer1Name() {
    return player1Name;
  }

  public void setPlayer1Name(String player1Name) {
    this.player1Name = player1Name;
  }

  public String getPlayer1Score() {
    return player1Score;
  }

  public void setPlayer1Score(String player1Score) {
    this.player1Score = player1Score;
  }

  public String getPlayer1Serve() {
    return player1Serve;
  }

  public void setPlayer1Serve(String player1Serve) {
    this.player1Serve = player1Serve;
  }

  public String getPlayer1Set1() {
    return player1Set1;
  }

  public void setPlayer1Set1(String player1Set1) {
    this.player1Set1 = player1Set1;
  }

  public String getPlayer1Set2() {
    return player1Set2;
  }

  public void setPlayer1Set2(String player1Set2) {
    this.player1Set2 = player1Set2;
  }

  public String getPlayer1Set3() {
    return player1Set3;
  }

  public void setPlayer1Set3(String player1Set3) {
    this.player1Set3 = player1Set3;
  }

  public String getPlayer2Name() {
    return player2Name;
  }

  public void setPlayer2Name(String player2Name) {
    this.player2Name = player2Name;
  }

  public String getPlayer2Score() {
    return player2Score;
  }

  public void setPlayer2Score(String player2Score) {
    this.player2Score = player2Score;
  }

  public String getPlayer2Serve() {
    return player2Serve;
  }

  public void setPlayer2Serve(String player2Serve) {
    this.player2Serve = player2Serve;
  }

  public String getPlayer2Set1() {
    return player2Set1;
  }

  public void setPlayer2Set1(String player2Set1) {
    this.player2Set1 = player2Set1;
  }

  public String getPlayer2Set2() {
    return player2Set2;
  }

  public void setPlayer2Set2(String player2Set2) {
    this.player2Set2 = player2Set2;
  }

  public String getPlayer2Set3() {
    return player2Set3;
  }

  public void setPlayer2Set3(String player2Set3) {
    this.player2Set3 = player2Set3;
  }
}
