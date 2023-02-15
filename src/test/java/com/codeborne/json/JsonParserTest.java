package com.codeborne.json;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static com.codeborne.json.assertions.JsonAssertions.assertThat;

class JsonParserTest {
  private final JsonParser parser = new JsonParser();

  @Test
  void plainNull() throws Exception {
    assertThat(parser.parse("null")).isNull();
  }

  @Test
  void valueNumber() throws IOException, JsonParseException {
    assertThat(parser.parse("  1")).isEqualTo(1);
    assertThat(parser.parse("2 ")).isEqualTo(2);
    assertThat(parser.parse(" 3")).isEqualTo(3);
  }

  @Test
  void valueBoolean() throws IOException, JsonParseException {
    assertThat(parser.parse("true")).isEqualTo(true);
    assertThat(parser.parse("false")).isEqualTo(false);
    assertThat(parser.parse(" false")).isEqualTo(false);
    assertThat(parser.parse(" true ")).isEqualTo(true);
  }

  @Test
  void valueNumericDouble() throws IOException, JsonParseException {
    assertThat(parser.parse("1.22")).isEqualTo(1.22);
  }
}
