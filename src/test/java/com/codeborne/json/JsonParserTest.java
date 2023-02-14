package com.codeborne.json;

import org.junit.jupiter.api.Test;

import static com.codeborne.json.JsonAssertions.assertThat;

class JsonParserTest {
  private final JsonParser parser = new JsonParser();

  @Test
  void nulls() throws Exception {
    assertThat(parser.parse("null")).isNull();
  }
}
