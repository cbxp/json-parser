package com.codeborne.json.assertions;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static com.codeborne.json.assertions.JsonAssertions.assertThat;

class JsonAssertTest {
  @Test
  void canExtractElementsFromMap() {
    assertThat(Map.of("user", Map.of("name", "me")))
      .extractingFromJson("user", "name").isEqualTo("me");
  }
}
