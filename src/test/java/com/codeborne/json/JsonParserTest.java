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
  void ignoreWhitespace_space() throws Exception {
    assertThat(parser.parse(" null ")).isNull();
  }

  @Test
  void ignoreWhitespace_linefeed() throws Exception {
    assertThat(parser.parse("\nnull\n")).isNull();
  }
}
