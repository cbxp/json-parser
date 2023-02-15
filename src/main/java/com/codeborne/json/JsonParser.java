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
    var buf = new StringBuilder();
    while (true){
      var nextChars = input.read();
      if (nextChars == -1) {
        break;
      }
      buf.append(Character.toChars(nextChars));
    }

    var token = buf.toString();

    if ("null".equals(token)) {
      return null;
    }

    if ("true".equals(token)) {
      return true;
    }

    if ("false".equals(token)) {
      return false;
    }

    if (token.startsWith("\"") && token.endsWith("\"")) {
      return token.substring(1, token.length() - 1);
    }

    return Integer.parseInt(buf.toString());
  }
}
