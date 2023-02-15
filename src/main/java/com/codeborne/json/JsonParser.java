package com.codeborne.json;

import org.intellij.lang.annotations.Language;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

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
            if (ch == '{' || ch == '[') {
                // TODO
            } else {
                result = readValue(input);
            }
        }

        return result;
    }

    public Object readValue(Reader input) throws IOException {
        Character ch = peek(input);

        if (ch == '"') {
            input.read();
            return readString(input, '"');
        } else {
            String string = readString(input, null);
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

    public String readString(Reader input, Character untilChar) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        Character ch;

        ch = read(input);

        while (ch != null && (untilChar == null || ch != untilChar)) {
            stringBuilder.append(ch);
            ch = read(input);
        }

        return stringBuilder.toString();
    }

    public Character read(Reader input) throws IOException {
        int read = input.read();
        return read == -1 ? null : (char) read;
    }

    public Character peek(Reader input) throws IOException {
        input.mark(1);
        Character ch = read(input);
        input.reset();
        return ch;
    }
}
