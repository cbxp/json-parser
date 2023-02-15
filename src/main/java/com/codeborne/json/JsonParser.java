package com.codeborne.json;

import com.codeborne.json.JsonTokenizer.JsonToken;
import com.codeborne.json.JsonTokenizer.TokenType;
import org.intellij.lang.annotations.Language;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import static com.codeborne.json.JsonTokenizer.TokenType.*;

/**
 * <a href="https://www.json.org/json-en.html">JSON specification</a>
 */
public class JsonParser {
  public Object parse(@Language("JSON") String input) throws IOException, JsonParseException {
    return parse(new StringReader(input));
  }

  private Map parseObject(JsonTokenizer tokenizer, Map collected) throws JsonParseException {

    JsonToken token = tokenizer.nextToken();

    if (token == null || token.type == COLON) throw new JsonParseException("Expected object closing token", 0);

    if (token.type == OBJ_CLOSING) return collected;

    expectToken(VALUE, "Expected property value token", token);
    expectToken(COLON, "Expected colon token", tokenizer.nextToken());

    JsonToken token2 = tokenizer.nextToken();

    if (token2 == null) throw new JsonParseException("Expected object start of value token", 0);

    if (token2.type == VALUE) {
      collected.put(token.value, token2.value);
    }

    if (token2.type == OBJ_START) {
      Map<String, String> subObject = new HashMap();
      collected.put(token.value, subObject);
      parseObject(tokenizer, subObject);
    }
    return collected;
  }

  public Object parse(Reader input) throws IOException, JsonParseException {
    JsonTokenizer tokenizer = new JsonTokenizer(input);

    JsonToken token = tokenizer.nextToken();
    if (token == null) return null;

    expectToken(OBJ_START, "Expected object start token", token);

    Map<String, String> collected = new HashMap();

    parseObject(tokenizer, collected);

    return collected;

//    JsonToken keyToke = tokenizer.nextToken();
//
//    if (keyToke != null && keyToke.type == OBJ_CLOSING) return Map.of();
//
//    expectToken(COLON, "Expected colon token", tokenizer.nextToken());
//
//    JsonToken value = tokenizer.nextToken();
//    expectToken(VALUE, "Expected property value token", value);
//
//    expectToken(OBJ_CLOSING, "Expected object closing token", tokenizer.nextToken());
//
//    return Map.of(keyToke.value, value.value);
  }

  private static void expectToken(TokenType expected, String messag, JsonToken token) throws JsonParseException {
    if (token == null || token.type != expected) throw new JsonParseException(messag, 0);
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
