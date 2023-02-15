package com.codeborne.json;

import org.intellij.lang.annotations.Language;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

/**
 * <a href="https://www.json.org/json-en.html">JSON specification</a>
 */
public class JsonParser {
    public Object parse(@Language("JSON") String input) throws IOException, JsonParseException {
        return parse(new StringReader(input));
    }

    public Object parse(Reader input) throws IOException, JsonParseException {
        StringBuffer buffer = new StringBuffer();
        while (true) {
            int character = input.read();
            if (character == -1) {
                break;
            }
            buffer.append((char) character);
        }
        String s = buffer.toString();
        if (s.equals("null")) {
            return null;
        }
        return s;
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
