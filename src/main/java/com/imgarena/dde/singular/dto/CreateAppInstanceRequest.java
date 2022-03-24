package com.imgarena.dde.singular.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreateAppInstanceRequest(
    String folder,
    @JsonProperty("apptemplate_id") Integer appTemplateId,
    String name
) {

}
