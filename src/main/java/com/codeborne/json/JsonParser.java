package com.codeborne.json;

import org.intellij.lang.annotations.Language;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Character.isWhitespace;

/**
 * <a href="https://www.json.org/json-en.html">JSON specification</a>
 */
public class JsonParser {
    public Object parse(@Language("JSON") String input) throws IOException {
        return parse(new StringReader(input));
    }

    public Object parse(Reader input) throws IOException {
        Object result = null;
        Character ch = peek(input);

        if (ch != null) {
            if (ch == '{') {
                result = readMap(input);
                // TODO
            } else if (ch == '[') {
                // TODO
            } else {
                result = readValue(input);
            }
        }

        return result;
    }

    private Map readMap(Reader input) throws IOException {
        HashMap<String, Object> result = new HashMap();
        input.read(); // curly bracket

        String key = readString(input);

        readWhitespaces(input);
        input.read(); // colon
        readWhitespaces(input);

        Object value = readValue(input);

        result.put(key, value);
        return result;
    }

    public Object readValue(Reader input) throws IOException {
        Character ch = peek(input);

        if (ch == '"') {
            return readString(input);
        } else {
            List<Character> untilChars = List.of('}', ',', '\n', '\r');

            String string = readTokenUntilChar(input, untilChars);
            if (string.equals("null")) {
                return null;
            } else if (string.equals("true")) {
                return true;
            } else if (string.equals("false")) {
                return false;
            } else {
                return Integer.valueOf(string);
            }
        }
    }

    public String readString(Reader input) throws IOException {
        input.read(); // double quote
        return readTokenUntilChar(input, List.of('"'));
    }

    private String readTokenUntilChar(Reader input, List<Character> untilChars) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        Character ch;

        ch = read(input);

        while (ch != null && !untilChars.contains(ch)) {
            stringBuilder.append(ch);
            ch = read(input);
        }

        return stringBuilder.toString();
    }

    private void readWhitespaces(Reader input) throws IOException {
        Character character = peek(input);

        while (isWhitespace(character)) {
            input.read();
            character = peek(input);
        }
    }

    private Character read(Reader input) throws IOException {
        int read = input.read();
        return read == -1 ? null : (char) read;
    }

    private Character peek(Reader input) throws IOException {
        input.mark(1);
        Character ch = read(input);
        input.reset();
        return ch;
    }
}
