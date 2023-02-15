package com.codeborne.json;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static com.codeborne.json.assertions.JsonAssertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class JsonParserTest {
  private final JsonParser parser = new JsonParser();

  @Test
  void plainNull() throws Exception {
    assertThat(parser.parse("null")).isNull();
  }

  @Test
  void plainTrue() throws Exception {
    assertThat(parser.parse("true")).isEqualTo(true);
  }
  @Test

  void plainFalse() throws Exception {
    assertThat(parser.parse("false")).isEqualTo(false);
  }

  @Test
  void plainString() throws Exception{
    assertThat(parser.parse("\"foo bar\"")).isEqualTo("foo bar");
  }

  @Test
  void plainString_quotationMark() throws IOException, JsonParseException {
    assertThat(parser.parse("\"\\\"\"")).isEqualTo("\"");
  }
  @Test
  void plainString_reverseSolidus() throws IOException, JsonParseException {
    assertThat(parser.parse("\"\\\\\"")).isEqualTo("\\");
  }

  @Test
  void plainString_invalidEscaping() throws IOException, JsonParseException {
    assertThatThrownBy(() -> parser.parse("\"\\\""))
        .isInstanceOf(JsonParseException.class)
        .hasMessage("Incorrect escaping syntax following \\");
  }

  @Test
  void ignoreWhitespace_space() throws Exception {
    assertThat(parser.parse(" null ")).isNull();
  }

  @Test
  void ignoreWhitespace_linefeed() throws Exception {
    assertThat(parser.parse("\nnull\n")).isNull();
  }

  @Test
  void ignoreWhitespace_carriageReturn() throws Exception {
    assertThat(parser.parse("\rnull\r")).isNull();
  }

  @Test
  void ignoreWhitespace_horizontalTab() throws Exception {
    assertThat(parser.parse("\tnull\t")).isNull();
  }
}
