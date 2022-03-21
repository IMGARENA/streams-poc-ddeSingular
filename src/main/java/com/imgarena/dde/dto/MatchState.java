package com.imgarena.dde.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public record MatchState(MatchStates state, Date locationTimestamp) { }
