package com.codeborne.json.assertions;

import org.assertj.core.api.AbstractObjectAssert;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class JsonAssert extends AbstractObjectAssert<JsonAssert, Object> {
  public JsonAssert(Object json) {
    super(json, JsonAssert.class);
  }

  public JsonAssert isListOfLength(int expectedLength) {
    isNotNull();
    assertThat((List<?>) actual).hasSize(expectedLength);
    return this;
  }

  public JsonAssert extractingFromJson(Object... jsonPath) {
    isNotNull();
    Object value = actual;
    for (Object step : jsonPath) {
      if (step instanceof String) {
        value = getFromMap(value, (String) step);
      }
      else if (step instanceof Integer) {
        value = getFromList(value, (Integer) step);
      }
      else {
        throw new IllegalArgumentException("Json path element can be String or Integer");
      }
    }
    return new JsonAssert(value);
  }

  private static Object getFromMap(Object value, String step) {
    if (!(value instanceof Map)) {
      throw new IllegalArgumentException(String.format("Cannot extract json element %s from %s", step, value));
    }
    @SuppressWarnings("unchecked")
    Map<String, Object> jsonObject = (Map<String, Object>) value;
    return jsonObject.get(step);
  }

  private static Object getFromList(Object value, int step) {
    if (!(value instanceof List<?>)) {
      throw new IllegalArgumentException(String.format("Cannot extract json element %s from %s", step, value));
    }
    List<?> jsonObject = (List<?>) value;
    return jsonObject.get(step);
  }
}
