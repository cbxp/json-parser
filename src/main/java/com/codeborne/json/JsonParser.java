package com.codeborne.json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.List;

import org.intellij.lang.annotations.Language;

/**
 * <a href="https://www.json.org/json-en.html">JSON specification</a>
 */
public class JsonParser {
  private static final List<Parser> PARSERS = List.of(new NullParser(), new BooleanParser(), new NumberParser(), new StringParser());

  public Object parse(@Language("JSON") String input) throws IOException, JsonParseException {
    return parse(new StringReader(input));
  }

  public Object parse(Reader input) throws IOException, JsonParseException {
    try (BufferedReader bufferedReader = new BufferedReader(input)) {
      bufferedReader.mark(1000);
      int character = bufferedReader.read();
      if (isEOF(character)) {
        throw new JsonParseException("Empty string", 0);
      }

      for (Parser parser : PARSERS) {
        if (parser.couldBe(character)) {
      bufferedReader.reset();
          return parser.parse(bufferedReader);
        }
      }
      throw new JsonParseException("Not yet implemented", -1);
    }
  }

  private boolean isEOF(int chr) {
    return chr == -1;
  }

  private boolean isWhitespace(int chr) {
    return Character.isWhitespace(chr);
  }
}
