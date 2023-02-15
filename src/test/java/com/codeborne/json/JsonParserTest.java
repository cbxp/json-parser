package com.codeborne.json;

import org.junit.jupiter.api.Test;

import static com.codeborne.json.assertions.JsonAssertions.assertThat;

class JsonParserTest {
  private final JsonParser parser = new JsonParser();

  @Test
  void plainNull() throws Exception {
    assertThat(parser.parse("null")).isNull();
  }

  @Test
  void valueString() throws Exception {
    assertThat(parser.parse("abc")).isEqualTo("abc");
  }
  @Test
  void valueNumber() throws Exception { // negative, positive larger than int, larger than long... maybe use BigDecimal
    assertThat(parser.parse("123")).isEqualTo(123);
    assertThat(parser.parse("-123")).isEqualTo(-123);
    assertThat(parser.parse("0")).isEqualTo(0);
  }
}
