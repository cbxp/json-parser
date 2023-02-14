package com.codeborne.json;

import java.io.InputStream;

public class JsonAssertions {
  public static JsonAssert assertThat(Object parsedJson) {
    return new JsonAssert(parsedJson);
  }

  static InputStream file(String fileName) {
    return JsonAssertions.class.getResourceAsStream('/' + fileName);
  }

}
