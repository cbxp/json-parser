package com.codeborne.json;

import org.intellij.lang.annotations.Language;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <a href="https://www.json.org/json-en.html">JSON specification</a>
 */
public class JsonParser {
  @Nullable
  private static Object readValue(Reader input) throws IOException {
    int value;
    String buffer = "";
    boolean isDouble = false;
    boolean exit = false;
    while ((value = input.read()) != -1) {
      String stringValue = String.valueOf(Character.toChars(value));
      switch (stringValue) {
        case "{":
          return readObject(input);
        case "[":
          return readArray(input);
        case ":":
        case "}":
        case "]":
        case ",":
          exit = true;
          break;
        case "\"":
          return readString(input);
        case " ":
        case "\n":
        case "\r":
        case "\t":
        case "\f":
          break;
        case ".":
        case "e":
          isDouble = true;
        default:
          buffer = buffer + stringValue;
      }
      if (exit) break;
    }
    switch (buffer) {
      case "":
        return null;
      case "null":
        return null;
      case "true":
        return true;
      case "false":
        return false;
      default:
        if (isDouble) {
          return Double.valueOf(buffer);
        } else {
          Long aLong = Long.parseLong(buffer);
          if (aLong > (long) Integer.MAX_VALUE) {
            return aLong;
          } else {
            return aLong.intValue();
          }
        }
    }
  }

  private static String readString(Reader input) throws IOException {
    int value;
    StringBuilder buffer = new StringBuilder();
    while ((value = input.read()) != -1) {
      String stringValue = String.valueOf(Character.toChars(value));
      switch (stringValue) {
        case "\\":
          buffer.append(readEscaped(input));
          break;
        case "\"":
          return buffer.toString();
        default:
          buffer.append(stringValue);
      }
    }
    return buffer.toString();
  }

  private static String readEscaped(Reader input) throws IOException {
    String value = String.valueOf(Character.toChars(input.read()));
    switch (value) {
      case "n":
        return "\n";
      case "t":
        return "\t";
      case "b":
        return "\b";
      case "r":
        return "\r";
      case "f":
        return "\f";
      case "u":
        return readUnicode(input);
      default:
        return value;
    }
  }

  private static String readUnicode(Reader input) throws IOException {
    StringBuilder buffer = new StringBuilder("0x");
    for (int i = 0; i < 4; i++) {
      buffer.append(String.valueOf(Character.toChars(input.read())));
    }
    return String.valueOf(Character.toChars(Integer.decode(buffer.toString())));
  }

  private static Map<String, Object> readObject(Reader input) throws IOException {
    Map<String, Object> o = new HashMap<>();
    Map<String, Object> v;
    while ((v = readKeyValue(input)) != null) {
      o.putAll(v);
    }
    return o;
  }

  private static Map<String, Object> readKeyValue(Reader input) throws IOException {
    var key = (String) readValue(input);
    readValue(input);
    var value = readValue(input);
    if (key == null) return null;
    return Map.of(key, value);
  }

  private static List<Object> readArray(Reader input) throws IOException {
    List<Object> objects = new ArrayList<>();
    Object v = null;
    while ((v = readValue(input)) != null) {
      objects.add(v);
    }
    return objects;
  }

  public Object parse(@Language("JSON") String input) throws IOException, JsonParseException {
    return parse(new StringReader(input));
  }

  public Object parse(Reader input) throws IOException, JsonParseException {
    return readValue(input);
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
