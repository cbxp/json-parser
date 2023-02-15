package com.codeborne.json;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import static com.codeborne.json.assertions.JsonAssertions.assertThat;

class JsonParserTest {
  private final JsonParser parser = new JsonParser();

  @Test
  void shouldParseNullToNull() throws Exception {
    assertThat(parser.parse("null")).isNull();
  }

  @Test
  void shouldParseEmptyStringToNull () throws Exception {
    assertThat(parser.parse("")).isNull();
  }

  @Test
  void shouldEmptyObjectToEmptyObject () throws Exception {
    assertTrue(parser.parse("{}").getClass().equals(Object.class));
    assertTrue(parser.parse("{}").getClass().getDeclaredFields().length == 0);
  }

  @Test
  void shouldEmptyObjectToEmptyList () throws Exception {
    assertTrue(parser.parse("[]").getClass().equals(ArrayList.class));
    assertTrue(((ArrayList)parser.parse("[]")).size() == 0);
  }

  // Should handle null values
  // "null" => null
  // "" => null

  //Should handle empty values
  // "{}" => empty object
  // "[]" => empty array




  // Should handle simple object
  // {"key":"value"}

  // Should handle simple array
  // [1, 2]
  // ["one", "two"]
  // [1, "two"]

  // Should handle object in array
  // [{"key1":"value1"}, {"key2":"value2"}]
  
  // Should handle object as value of key
  // {"key": {"key1": "value1"}}

  // Should handle array as value of key
  // {"key": [1, 2]}



  // Should handle simple strings and numbers
  // "Test" => "Test"
  // 0 => 0
  // 100 => 100
}
