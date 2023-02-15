package com.codeborne.json;

import org.intellij.lang.annotations.Language;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

/**
 * <a href="https://www.json.org/json-en.html">JSON specification</a>
 */
public class JsonParser {
  public Object parse(@Language("JSON") String input) throws IOException, JsonParseException {
    return parse(new StringReader(input));
  }

  public Object parse(Reader input) throws IOException, JsonParseException {
    int c = input.read();
    if (c == -1) {
      throw new JsonParseException("Empty input", 0);
    }
    boolean isString = isString(c);
    input.reset();
    String result = readToString(input);
    if (isString) {
      return result.substring(1, result.length() - 1);
    }
    return switch (result) {
      case "null" -> null;
      case "true" -> true;
      case "false" -> false;
      default -> result;
    };
  }

  private boolean isString(int c) {
    return c == '"';
  }

  private String readToString(Reader input) throws IOException {
    StringBuilder sb = new StringBuilder();
    for (int c = input.read(); c != -1; c = input.read()) {
      sb.append((char) c);
    }
    return sb.toString();
  }
}

/*
JsonParser.kt, or use Ctrl+Shift+Alt+K to convert

class JsonParser {
  fun parse(@Language("JSON") input: String) = parse(StringReader(input))

  fun parse(input: Reader): Any? {
    TODO("implement me")
  }
}
*/
