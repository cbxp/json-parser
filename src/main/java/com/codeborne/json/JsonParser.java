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
    int value;
    String buffer = "";
    boolean isDouble = false;
    boolean isString = false;
    while ((value = input.read()) != -1) {
      String stringValue = String.valueOf(Character.toChars(value));
      switch (stringValue) {
        case "\\":
          buffer = buffer + String.valueOf(Character.toChars(input.read()));
          break;
        case "\"":
          isString = true;
          break;
        case " ":
          if (!isString) break;
        case ".":
          isDouble = true;
        default:
          buffer = buffer + stringValue;
      }

      System.out.println("read = " + value);
    }
    switch (buffer) {
      case "":
        if (isString) {
          return "";
        }
        return null;
      case "null":
        return null;
      case "true":
        return true;
      case "false":
        return false;
      default:
        if (isString) return buffer;
        if (isDouble) {
          return Double.valueOf(buffer);
        } else {
          Long aLong = Long.parseLong(buffer);
          if (aLong > (long)Integer.MAX_VALUE) {
            return aLong;
          } else {
            return aLong.intValue();
          }
        }
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
