package com.codeborne.json;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static com.codeborne.json.JsonAssertions.assertThat;
import static com.codeborne.json.JsonAssertions.file;


class JsonParserTest {
  private final JsonParser parser = new JsonParser();

  @Test
  void number() throws IOException {
    assertThat(parser.parse("42")).isEqualTo(42);
  }

  @Test
  void string() throws IOException {
    assertThat(parser.parse("\"tere\"")).isEqualTo("tere");
  }

  @Test
  void map() throws IOException {
    assertThat(parser.parse(file("map.json")))
      .extractingFromJson("user", "firstName").isEqualTo("John");
  }

  @Test
  void list() throws IOException {
    assertThat(parser.parse(file("list.json"))).isEqualTo(List.of(33));
  }
}