package com.imgarena.dde.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import java.text.SimpleDateFormat;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = Id.NAME, include = As.EXISTING_PROPERTY, property = "eventElementType")
@JsonSubTypes({
    @JsonSubTypes.Type(value = MatchStatusWrapper.class, name = "MatchStatusUpdate")
})
public class EventStreamHeader {

   private  Date timestamp;
   private  Integer seqNum;
   private  Date receivedAt;
   private  EventElementType eventElementType;

  public Date getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Date timestamp) {
    this.timestamp = timestamp;
  }

  public Integer getSeqNum() {
    return seqNum;
  }

  public void setSeqNum(Integer seqNum) {
    this.seqNum = seqNum;
  }

  public Date getReceivedAt() {
    return receivedAt;
  }

  public void setReceivedAt(Date receivedAt) {
    this.receivedAt = receivedAt;
  }

  public EventElementType getEventElementType() {
    return eventElementType;
  }

  public void setEventElementType(EventElementType eventElementType) {
    this.eventElementType = eventElementType;
  }

  @Override
  public String toString() {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
    return String.format(
        "seqNum: %d%ntimestamp: %s%neventElementType: %s",
        seqNum, dateFormat.format(timestamp), eventElementType);
  }
}
