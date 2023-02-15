package com.codeborne.json;

import org.intellij.lang.annotations.Language;

import java.io.BufferedReader;
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
    BufferedReader bufferedReader = new BufferedReader(input);
    String line;
    StringBuilder result = new StringBuilder();
    while ((line = bufferedReader.readLine()) != null) {
      result.append(line);
    }
    String string = result.toString();
    System.out.println(string);

    switch (string) {
      case "null":
        return null;
      default:
        return Boolean.parseBoolean(string);
    }

  }
}
