package com.codeborne.json;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Map;

import static com.codeborne.json.assertions.JsonAssertions.assertThat;

class JsonParserTest {
  private final JsonParser parser = new JsonParser();

  @Test
  void plainNull() throws Exception {
    assertThat(parser.parse("null")).isNull();
  }

  @Test
  void emptyObject() throws IOException, JsonParseException {
    assertThat(parser.parse("{}")).isEqualTo(Map.of());
  }
}
