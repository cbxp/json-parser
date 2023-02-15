package com.codeborne.json;

import org.junit.jupiter.api.Test;

import java.io.StringReader;

import static com.codeborne.json.JsonTokenizer.JsonToken;
import static com.codeborne.json.JsonTokenizer.TokenType.*;
import static com.codeborne.json.JsonTokenizer.UnexpectedEndOfFile;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

class JsonTokenizerTest {

  @Test
  void nothing() {
    JsonTokenizer tokenizer = new JsonTokenizer(new StringReader(""));
    assertThat(tokenizer.nextToken()).isNull();
  }

  @Test
  void objectStartSimple() {
    JsonTokenizer tokenizer = new JsonTokenizer(new StringReader("{"));
    assertThat(tokenizer.nextToken()).isEqualTo(new JsonToken(OBJ_START));
  }

  @Test
  void objectClosingSimple() {
    JsonTokenizer tokenizer = new JsonTokenizer(new StringReader("}"));
    assertThat(tokenizer.nextToken()).isEqualTo(new JsonToken(OBJ_CLOSING));
  }

  @Test
  void emptyObject() {
    JsonTokenizer tokenizer = new JsonTokenizer(new StringReader("{}"));
    assertThat(tokenizer.nextToken()).isEqualTo(new JsonToken(OBJ_START));
    assertThat(tokenizer.nextToken()).isEqualTo(new JsonToken(OBJ_CLOSING));
    assertThat(tokenizer.nextToken()).isNull();
  }

  @Test
  void colon() {
    JsonTokenizer tokenizer = new JsonTokenizer(new StringReader(":"));
    assertThat(tokenizer.nextToken()).isEqualTo(new JsonToken(COLON));
    assertThat(tokenizer.nextToken()).isNull();
  }

  @Test
  void string() {
    JsonTokenizer tokenizer = new JsonTokenizer(new StringReader("\"hello\""));
    assertThat(tokenizer.nextToken()).isEqualTo(new JsonToken(VALUE, "hello"));
    assertThat(tokenizer.nextToken()).isNull();
  }

  @Test
  void brokenString() {
    JsonTokenizer tokenizer = new JsonTokenizer(new StringReader("\"hello"));

    try {
      tokenizer.nextToken();
      fail("should throw exception due to incomplete string");
    } catch (UnexpectedEndOfFile e) {
    }
  }

}