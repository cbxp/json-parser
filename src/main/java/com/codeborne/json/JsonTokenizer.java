package com.codeborne.json;

import java.io.IOException;
import java.io.PushbackReader;
import java.io.Reader;
import java.util.Objects;

public class JsonTokenizer {

  private final PushbackReader reader;

  public JsonTokenizer(Reader r) {
    reader = new PushbackReader(r);
  }

  private char nextSymbol(boolean endOfFileAllowed) {
    int result = 0;
    try {
      result = reader.read();
      if (result == -1) {
        throw endOfFileAllowed ? new EndOfFile() : new UnexpectedEndOfFile();
      }
      return (char) result;
    } catch (IOException e) {
      throw new UnexpectedEndOfFile();
    }
  }

  public JsonToken nextToken() {
    try {
      char symbol = nextSymbol(true);

      while (Character.isWhitespace(symbol)) symbol = nextSymbol(true);

      if (symbol == '{') return new JsonToken(TokenType.OBJ_START);
      if (symbol == '}') return new JsonToken(TokenType.OBJ_CLOSING);
      if (symbol == ':') return new JsonToken(TokenType.COLON);

      if (symbol == '"') {
        StringBuffer buffer = new StringBuffer();
        while (true) {
          symbol = nextSymbol(false);
          if (symbol == '"') {
            return new JsonToken(TokenType.VALUE, buffer.toString());
          }
          buffer.append(symbol);
        }
      }

      return null;
    } catch (EndOfFile e) {
      return null;
    }
  }

  enum TokenType {OBJ_START, OBJ_CLOSING, COLON, VALUE}

  protected static class JsonToken {

    public final JsonTokenizer.TokenType type;
    public final String value;

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      JsonToken jsonToken = (JsonToken) o;
      return type == jsonToken.type && Objects.equals(value, jsonToken.value);
    }

    @Override
    public int hashCode() {
      return Objects.hash(type, value);
    }

    public JsonToken(JsonTokenizer.TokenType type, String value) {
      this.type = type;
      this.value = value;
    }

    public JsonToken(JsonTokenizer.TokenType type) {
      this.type = type;
      this.value = null;
    }

    @Override
    public String toString() {
      return "JsonToken{" +
          "type=" + type +
          ", value='" + value + '\'' +
          '}';
    }
  }

  protected static class EndOfFile extends RuntimeException {}
  protected static class UnexpectedEndOfFile extends RuntimeException {}
}
