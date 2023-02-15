package com.codeborne.json;

import org.intellij.lang.annotations.Language;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.stream.Collectors;

/**
 * <a href="https://www.json.org/json-en.html">JSON specification</a>
 */
public class JsonParser {
  public Object parse(@Language("JSON") String input) throws IOException, JsonParseException {
    return parse(new StringReader(input));
  }

  public Object parse(Reader input) throws IOException, JsonParseException {
    try (BufferedReader bufferedReader = new BufferedReader(input)) {
      bufferedReader.mark(1000);
      int character = bufferedReader.read();
      if (isEOF(character)) {
        throw new JsonParseException("Empty string", 0);
      }

      bufferedReader.reset();
      if (couldBeNull(character)) {
        return parseNull(bufferedReader);
      } else if (couldBeBoolean(character)) {
        return parseBoolean(bufferedReader);
      } else if (couldBeNumber(character)) {
        return parseNumber(bufferedReader);
      }
      throw new JsonParseException("Not yet implemented", -1);
    }
  }

  private boolean isEOF(int chr) {
    return chr == -1;
  }

  private boolean isWhitespace(int chr) {
    return Character.isWhitespace(chr);
  }

  private boolean couldBeNull(int character) {
    return character == 'n';
  }

  private Object parseNull(BufferedReader reader) throws JsonParseException {
    String value = reader.lines().collect(Collectors.joining());
    if ("null".equals(value)) {
      return null;
    }
    throw new JsonParseException("Unknown value %s".formatted(value), -1);
  }

  private boolean couldBeBoolean(int character) {
    return character == 't' || character == 'f';
  }

  private Object parseBoolean(BufferedReader reader) throws JsonParseException {
    String value = reader.lines().collect(Collectors.joining());
    if ("true".equals(value)) {
      return true;
    } else if ("false".equals(value)) {
      return false;
    }
    throw new JsonParseException("Unknown value %s".formatted(value), -1);
  }

  private boolean couldBeNumber(int character) {
    return character == '-' || Character.isDigit(character);
  }

  private Object parseNumber(BufferedReader reader) {
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
