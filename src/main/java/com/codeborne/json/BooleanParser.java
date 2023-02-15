package com.codeborne.json;

import java.io.BufferedReader;
import java.util.stream.Collectors;

public class BooleanParser implements Parser {

  @Override
  public boolean couldBe(int character) {
    return character == 't' || character == 'f';
  }

  @Override
  public Object parse(BufferedReader reader) throws JsonParseException {
    String value = reader.lines().collect(Collectors.joining());
    if ("true".equals(value)) {
      return true;
    } else if ("false".equals(value)) {
      return false;
    }
    throw new JsonParseException("Unknown value %s".formatted(value), -1);
  }

}
