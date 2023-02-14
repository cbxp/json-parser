package com.codeborne.json;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

/**
 * <a href="https://www.json.org/json-en.html">JSON specification</a>
 */
public class JsonParser {
  public Object parse(String input) throws IOException {
    return parse(new StringReader(input));
  }

  public Object parse(Reader input) throws IOException {
    StringBuilder result = new StringBuilder();
    for (int c = input.read(); c > -1; c = input.read()) {
      result.append((char) c);
    }
    return result.toString();
  }
}