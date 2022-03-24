package com.imgarena.dde.singular.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SingularAppInstance(
    Long id,
    String name,
    String token,
    @JsonProperty("onair_url") String onAirURL
) {

}
