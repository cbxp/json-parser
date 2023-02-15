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

  public JsonToken nextToken() {
    try {
      int result = reader.read();
      if (result == -1) return null;
      char symbol = (char) result;

      if (symbol == '{') return new JsonToken(TokenType.OBJ_START);
      if (symbol == '}') return new JsonToken(TokenType.OBJ_CLOSING);
      if (symbol == ':') return new JsonToken(TokenType.COLON);


      return null;
    } catch (IOException e) {
      throw new RuntimeException(e);
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
  }
}
