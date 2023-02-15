package com.codeborne.json;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import org.junit.jupiter.api.Test;

class StringParserTest {
  private final StringParser parser = new StringParser();

  @Test
  void couldBe() {
    assertThat(parser.couldBe('"')).isTrue();
    assertThat(parser.couldBe('e')).isFalse();
  }

  @Test
  void parse() throws IOException, JsonParseException {
    assertThat(parser.parse(getReader("\"\""))).isEqualTo("");
    assertThat(parser.parse(getReader("\"here's an escaped quote: \\\"\""))).isEqualTo("here's an escaped quote: \"");
  }

  private BufferedReader getReader(String input) {
    return new BufferedReader(new StringReader(input));
  }

}
