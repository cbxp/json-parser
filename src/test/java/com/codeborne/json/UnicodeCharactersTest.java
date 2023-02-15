package com.codeborne.json;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static com.codeborne.json.assertions.JsonAssertions.assertThat;
import static com.codeborne.json.assertions.JsonAssertions.file;

public class UnicodeCharactersTest {
  private final JsonParser parser = new JsonParser();

  @Test
  void unicodeCharacters() throws IOException, JsonParseException {
    Object json = parser.parse(file("unicode.json"));
    assertThat(json).extractingFromJson("data").isEqualTo("\\uD83D\\uDE03");
    assertThat(json).extractingFromJson("\\uD83D\\uDE03").isEqualTo("are you crazy?");
    assertThat(json).extractingFromJson("mood").isEqualTo("It's \\\"fun\\\"");
    assertThat(json).extractingFromJson("song").isEqualTo("It's a sin");
    assertThat(json).extractingFromJson("a'la").isEqualTo("Carte");
  }

}
