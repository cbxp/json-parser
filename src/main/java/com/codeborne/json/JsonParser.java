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
    int value = -1;
    String buffer = "";
    while ((value = input.read()) != -1) {
      switch (value) {
        case 32:
          break;
        default:
          buffer = buffer + (char) value;
      }
      System.out.println("read = " + value);
    }
    switch (buffer) {
      case "": return null;
      case "null": return null;
      case "true": return true;
      case "false": return false;
      default:
          return Integer.valueOf(buffer);
    }
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
