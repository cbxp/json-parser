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
    // TODO implement me
    return null;
  }
}

/*
Or in Kotlin:

class JsonParser {
  fun parse(input: String) = parse(StringReader(input))

  fun parse(input: Reader): Any? {
    TODO("implement me")
  }
}
*/
