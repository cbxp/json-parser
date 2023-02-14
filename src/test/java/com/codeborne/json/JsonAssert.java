package com.codeborne.json;

import org.assertj.core.api.AbstractObjectAssert;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class JsonAssert extends AbstractObjectAssert<JsonAssert, Object> {
  public JsonAssert(Object json) {
    super(json, JsonAssert.class);
  }

  public JsonAssert isArrayOfLength(int expectedLength) {
    isNotNull();
    assertThat((Object[]) actual).hasSize(expectedLength);
    return this;
  }

  @SuppressWarnings("unchecked")
  public JsonAssert extractingFromJson(String... jsonPath) {
    isNotNull();
    Object value = actual;
    for (String s : jsonPath) {
      if (!(value instanceof Map)) {
        throw new IllegalArgumentException(String.format("Cannot extract json element %s from %s", s, value));
      }
      Map<String, Object> jsonObject = (Map<String, Object>) value;
      value = jsonObject.get(s);
    }
    return new JsonAssert(value);
  }
}
