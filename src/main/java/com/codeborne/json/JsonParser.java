package com.codeborne.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.intellij.lang.annotations.Language;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

/**
 * <a href="https://www.json.org/json-en.html">JSON specification</a>
 */
public class JsonParser {
  private final ObjectMapper mapper = new ObjectMapper();

  public Object parse(@Language("JSON") String input) throws IOException {
    return parse(new StringReader(input));
  }

  public Object parse(Reader input) throws IOException {
    return mapper.readValue(input, Object.class);
  }
}