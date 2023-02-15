package com.codeborne.json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.intellij.lang.annotations.Language;

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
      if (isEOF(bufferedReader.read())) {
        throw new JsonParseException("Empty string", 0);
      }
      bufferedReader.reset();

      return new ValueParser().parse(bufferedReader);
    }
  }

  private boolean isEOF(int chr) {
    return chr == -1;
  }

}
