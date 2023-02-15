package com.codeborne.json;

import org.junit.jupiter.api.Test;

import java.io.StringReader;

import static com.codeborne.json.JsonTokenizer.*;
import static com.codeborne.json.JsonTokenizer.TokenType.*;
import static org.assertj.core.api.Assertions.assertThat;

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
}