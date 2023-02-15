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
  private static Object readValue(Reader input, Boolean isString) throws IOException {
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
          if (!isString) {
            exit = true;
            break;
          }
        case "\\":
          //TODO other escapes
          buffer = buffer + String.valueOf(Character.toChars(input.read()));
          break;
        case "\"":
          if (isString) return buffer;
          else return readValue(input, true);
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
          if (aLong > (long) Integer.MAX_VALUE) {
            return aLong;
          } else {
            return aLong.intValue();
          }
        }
    }
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
    var key = (String)readValue(input, false);
    readValue(input, false);
    var value = readValue(input, false);
    if (key == null) return null;
    return Map.of(key, value);
  }

  private static List<Object> readArray(Reader input) throws IOException {
    List<Object> objects = new ArrayList<>();
    Object v = null;
    while ((v = readValue(input, false)) != null) {
      objects.add(v);
    }
    return objects;
  }

  public Object parse(@Language("JSON") String input) throws IOException, JsonParseException {
    return parse(new StringReader(input));
  }

  public Object parse(Reader input) throws IOException, JsonParseException {
    return readValue(input, false);
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
