package com.codeborne.json;

import org.intellij.lang.annotations.Language;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * <a href="https://www.json.org/json-en.html">JSON specification</a>
 */
public class JsonParser {
  public Object parse(@Language("JSON") String input) throws IOException, JsonParseException {
    return parse(new StringReader(input));
  }

  public Object parse(Reader input) throws IOException, JsonParseException {
    return readValue(input);
  }

  @Nullable
  private static Object readValue(Reader input) throws IOException {
    int value;
    String buffer = "";
    boolean isDouble = false;
    boolean isString = false;
    boolean exit = false;
    while ((value = input.read()) != -1) {
      String stringValue = String.valueOf(Character.toChars(value));
      switch (stringValue) {
        case "[":
          return readArray(input);
        case "]":
        case ",":
          if (!isString) {
            exit = true;
            break;
          }
        case "\\":
          //TODO other escapes
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
      if (exit) break;
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

  private static List<Object> readArray(Reader input) throws IOException {
    List<Object> objects = new ArrayList<>();
    Object v = null;
    while ((v = readValue(input)) != null) {
      objects.add(v);
    }
    return objects;
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
