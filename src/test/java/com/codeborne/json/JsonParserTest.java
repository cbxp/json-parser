package com.codeborne.json;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static com.codeborne.json.assertions.JsonAssertions.assertThat;
import static java.util.Collections.emptyList;

class JsonParserTest {
  private final JsonParser parser = new JsonParser();

  @Test
  void plainNull() throws Exception {
    assertThat(parser.parse("null")).isNull();
  }

  @Test
  void plainNumber() throws IOException, JsonParseException {
    assertThat(parser.parse("123")).isEqualTo(123);
  }

  @Test
  void plainString() throws IOException, JsonParseException {
    assertThat(parser.parse("\"abc\"")).isEqualTo("abc");
  }

  @Test
  void emptyArray() throws IOException, JsonParseException {
    assertThat(parser.parse("[   ]")).isEqualTo(emptyList());
  }
}
