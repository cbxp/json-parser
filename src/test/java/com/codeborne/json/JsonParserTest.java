package com.codeborne.json;

import org.intellij.lang.annotations.Language;
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

  @Test
  void simpleObject() throws IOException, JsonParseException {
    @Language("JSON") String json = """
        {
          "key":"value"
        }
        """;

    assertThat(parser.parse(json)).isEqualTo(Map.of("key", "value"));
  }
}
