package com.codeborne.json;

import org.intellij.lang.annotations.Language;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * <a href="https://www.json.org/json-en.html">JSON specification</a>
 */
public class JsonParser {
  public Object parse(@Language("JSON") String input) throws IOException, JsonParseException {
    return parse(new StringReader(input));
  }

  public Object parse(Reader input) throws IOException, JsonParseException {
    var buf = new StringBuilder();
    while (true){
      var nextChars = input.read();
      if (nextChars == -1) {
        break;
      }
      buf.append(Character.toChars(nextChars));
    }

    var token = buf.toString();

    if (token.equals("null")) {
      return null;
    }


    if (token.startsWith("\"") && token.endsWith("\"")) {
      return token.substring(1, token.length() - 1);
    }

    return Integer.parseInt(buf.toString());
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
