package com.codeborne.json;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * <a href="https://www.json.org/json-en.html">JSON specification</a>
 */
public class JsonParser {
  public Object parse(String input) throws IOException {
    return parse(new ByteArrayInputStream(input.getBytes(UTF_8)));
  }

  public Object parse(InputStream input) throws IOException {
    StringBuilder result = new StringBuilder(input.available());
    for (int c = input.read(); c > -1; c = input.read()) {
      result.append((char) c);
    }
    return result.toString();
  }
}