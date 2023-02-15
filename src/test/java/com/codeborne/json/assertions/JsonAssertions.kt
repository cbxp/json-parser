package com.codeborne.json.assertions;

import java.io.InputStreamReader;
import java.io.Reader;

import static java.nio.charset.StandardCharsets.UTF_8;

public class JsonAssertions {
  public static JsonAssert assertThat(Object parsedJson) {
    return new JsonAssert(parsedJson);
  }

  static Reader file(String fileName) {
    return new InputStreamReader(JsonAssertions.class.getResourceAsStream('/' + fileName), UTF_8);
  }
}
