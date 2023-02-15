package com.codeborne.json;

import java.io.BufferedReader;
import java.util.stream.Collectors;

public class NumberParser implements Parser {

  @Override
  public boolean couldBe(int character) {
    return character == '-' || Character.isDigit(character);
  }

  @Override
  public Object parse(BufferedReader reader) throws JsonParseException {
    String value = reader.lines().collect(Collectors.joining());
    if (value.contains(".")) {
      return Double.parseDouble(value);
    } else {
      try {
        return Integer.parseInt(value);
      } catch (NumberFormatException e) {
        return Long.parseLong(value);
      }
    }
  }

}
