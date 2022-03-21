package com.imgarena.dde.dto;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import java.util.Map;

public class Server {

  private TeamNames team;

  public static JsonDeserializer<Server> deserializer() {
    return new JsonDeserializer<Server>() {
      @Override
      public Server deserialize(
          JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        Server res = new Server();
        if (jsonParser.getTextLength() == 1) {
          Map<String, String> value = jsonParser.getCodec().readValue(jsonParser, Map.class);
          res.setTeam(TeamNames.valueOf(value.get("team")));
        } else {
          res.setTeam(TeamNames.valueOf(jsonParser.getText().substring(0, 5)));
        }
        return res;
      }
    };
  }

  public TeamNames getTeam() {
    return team;
  }

  public void setTeam(TeamNames team) {
    this.team = team;
  }
}
