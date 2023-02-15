package com.codeborne.json;

import org.intellij.lang.annotations.Language;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.function.Predicate.not;

/**
 * <a href="https://www.json.org/json-en.html">JSON specification</a>
 */
public class JsonParser {
  public Object parse(@Language("JSON") String input) throws IOException, JsonParseException {
    return parse(new StringReader(input));
  }

  public Object parse(Reader input) throws IOException, JsonParseException {
    try (BufferedReader reader = new BufferedReader(input)) {
      String line = reader.lines()
          .map(String::trim)
          .collect(Collectors.joining());
      if (line.length() < 2) {
        throw new JsonParseException("Invalid JSON", -1);
      }
      if ("null".equals(line)) {
        return null;
      }
      else if ("true".equals(line)) {
        return true;
      }
      else if ("false".equals(line)) {
        return false;
      }
      else if (line.startsWith("\"") && line.endsWith("\"")) {
        return unescape(line.substring(1, line.length()-1));
      }
      else if (line.startsWith("[") && line.endsWith("]")) {
        return Arrays.stream(line.substring(1, line.length()-1).split(","))
            .filter(not(String::isEmpty))
            .collect(Collectors.toList());
      }
      else {
        throw new JsonParseException("Invalid JSON", -1);
      }
    }
  }

  private String unescape(String value) throws JsonParseException {
    StringBuilder result = new StringBuilder();
    char[] chars = value.toCharArray();
    for (int i = 0; i < value.toCharArray().length; i++) {
      char c = chars[i];
      if (c == '\\') {
        try {
          result.append(chars[i+1]);
        } catch (ArrayIndexOutOfBoundsException e) {
          throw new JsonParseException("Incorrect escaping syntax following \\", -1);
        }
        i++;
        continue;
      }
      result.append(c);
    };
    return result.toString();
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
