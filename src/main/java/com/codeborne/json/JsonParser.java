package com.codeborne.json;

import org.intellij.lang.annotations.Language;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

import static java.util.Collections.emptyList;

/**
 * <a href="https://www.json.org/json-en.html">JSON specification</a>
 */
public class JsonParser {
  public Object parse(@Language("JSON") String input) throws IOException {
    return parse(new StringReader(input));
  }

  private void parseColon(JsonScanner scanner) throws IOException {
    if (!scanner.hasNext()) {
      throw new RuntimeException("Unexpected end of JSON");
    }
    var token = scanner.scan();
    if (!":".equals(token)) {
      throw new RuntimeException("Colon expected: " + token);
    }
  }

  private Object parseList(JsonScanner scanner) throws IOException {
    List<Object> result = new ArrayList<>();
    while (scanner.hasNext()) {
      var token = scanner.scan();
      if ("]".equals(token)) {
        return result.isEmpty() ? emptyList() : result;
      }
      if (",".equals(token)) {
        continue;
      }
      var value = parseValue(token, scanner);
      result.add(value);
    }
    throw new RuntimeException("Unexpected end of JSON");
  }

  private Map<String, Object> parseObject(JsonScanner scanner) throws IOException {
    Map<String, Object> result = new HashMap<>();
    while (scanner.hasNext()) {
      var token = scanner.scan();

      if ("}".equals(token)) {
        return result;
      }
      if (",".equals(token)) {
        continue;
      }
      if (token.startsWith("\"") && token.endsWith("\"")) {
        var key = token.substring(1, token.length() - 1);
        parseColon(scanner);
        var valueToken = scanner.scan();
        var value = parseValue(valueToken, scanner);
        result.put(key, value);
      }
    }
    throw new RuntimeException("Unexpected end of JSON");
  }

  private Object parseValue(String token, JsonScanner scanner) throws IOException {
    if (token == null) {
      throw new RuntimeException("Unexpected end of JSON");
    }
    if ("[".equals(token)) {
      return parseList(scanner);
    }
    if ("{".equals(token)) {
      return parseObject(scanner);
    }
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
    try {
      var number = new BigDecimal(token);
      var integer = number.toBigIntegerExact();
      if (integer.compareTo(BigInteger.valueOf(Integer.MIN_VALUE)) >= 0 && integer.compareTo(BigInteger.valueOf(Integer.MAX_VALUE)) <= 0) {
        return integer.intValueExact();
      }
      return integer.longValueExact();
    } catch (ArithmeticException e) {
      return Double.valueOf(token);
    } catch (NumberFormatException e) {
      throw new RuntimeException("Unexpected token: " + token);
    }
  }

  public Object parse(Reader input) throws IOException {
    var scanner = new JsonScanner(input);
    var token = scanner.scan();
    return parseValue(token, scanner);
  }
}

class JsonScanner {
  private final Reader reader;
  private String nextChar = null;

  JsonScanner(Reader reader) throws IOException {
    this.reader = reader;
    read();
  }

  boolean hasNext() {
    return nextChar != null;
  }

  private String read() throws IOException {
    var currentChar = nextChar;
    var nextCodepoint = reader.read();
    if (nextCodepoint == -1) {
      nextChar = null;
    } else {
      nextChar = String.valueOf(Character.toChars(nextCodepoint));
    }
    return currentChar;
  }

  private String readString() throws IOException {
    var buf = new StringBuilder();

    while (true) {
      if (buf.length() == 0 && !"\"".equals(nextChar)) {
        throw new RuntimeException("Opening quote expected");
      }
      var ch = read();
      buf.append(ch);
      if (buf.length() > 1 && "\"".equals(ch)) {
        return buf.toString();
      }
      if (nextChar == null) {
        throw new RuntimeException("Unexpected end of string");
      }
    }
  }

  String scan() throws IOException {
    var buf = new StringBuilder();
    boolean isWhitespacePrefix = true;

    while (true) {
      if (nextChar == null) {
        return buf.toString();
      }

      if (nextChar.isBlank() && isWhitespacePrefix) {
        read();
        continue;
      }

      isWhitespacePrefix = false;

      if (buf.length() == 0) {
        if ("\"".equals(nextChar)) {
          return readString();
        }
        if (List.of("[", "]", "{", "}", ",", ":").contains(nextChar)) {
          return read();
        }
      }

      buf.append(read());

      if (nextChar != null && List.of("]", "}", ",").contains(nextChar)) {
        return buf.toString();
      }
    }
  }
}

