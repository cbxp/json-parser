package com.codeborne.json;

import java.io.BufferedReader;
import java.util.stream.Collectors;

public class NullParser implements Parser {

  @Override
  public boolean couldBe(int character) {
    return character == 'n';
  }

  @Override
  public Object parse(BufferedReader reader) throws JsonParseException {
    String value = reader.lines().collect(Collectors.joining());
    if ("null".equals(value)) {
      return null;
    }
    throw new JsonParseException("Unknown value %s".formatted(value), -1);
  }
}
