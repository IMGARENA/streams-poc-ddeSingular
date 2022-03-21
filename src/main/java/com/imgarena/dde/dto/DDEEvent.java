package com.imgarena.dde.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = Id.NAME, include = As.EXISTING_PROPERTY, property = "eventElementType")
@JsonSubTypes({
    @JsonSubTypes.Type(value = MatchStatusWrapper.class, name = "MatchStatusUpdate"),
    @JsonSubTypes.Type(value = PointScore.class, name = "PointScored")
})
public interface DDEEvent {

  String eventElementType = "";
  String matchTime = "";
  Integer seqNum = 0;
}
