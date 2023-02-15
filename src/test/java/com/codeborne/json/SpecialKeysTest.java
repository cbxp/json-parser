package com.codeborne.json;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static com.codeborne.json.assertions.JsonAssertions.assertThat;
import static com.codeborne.json.assertions.JsonAssertions.file;

public class SpecialKeysTest {
  private final JsonParser parser = new JsonParser();


  @Test
  void specialKeys() throws IOException, JsonParseException {
    Object json = parser.parse(file("special-keys.json"));
    assertThat(json).extractingFromJson("foo").isEqualTo(List.of("bar", "baz"));
    assertThat(json).extractingFromJson("").isEqualTo(0);
    assertThat(json).extractingFromJson("a/b").isEqualTo(1);
    assertThat(json).extractingFromJson("c%d").isEqualTo(2);
    assertThat(json).extractingFromJson("e^f").isEqualTo(3);
    assertThat(json).extractingFromJson("g|h").isEqualTo(4);
    assertThat(json).extractingFromJson("i\\j").isEqualTo(5);
    assertThat(json).extractingFromJson("k\"l").isEqualTo(6);
    assertThat(json).extractingFromJson(" ").isEqualTo(7);
    assertThat(json).extractingFromJson("m~n").isEqualTo(8);
    assertThat(json).extractingFromJson("tab:\\nok?").isEqualTo("ok:)");
    assertThat(json).extractingFromJson("obj", "key").isEqualTo("value");
    assertThat(json).extractingFromJson("obj", "other~key", "another/key", 0).isEqualTo("val");
    assertThat(json).extractingFromJson("obj", "", "").isEqualTo("empty key of an object with an empty key");
    assertThat(json).extractingFromJson("obj", "", "subKey").isEqualTo("Some other value");
  }

}
