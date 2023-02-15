package com.codeborne.json;

import org.intellij.lang.annotations.Language;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import static java.lang.Integer.valueOf;

/**
 * <a href="https://www.json.org/json-en.html">JSON specification</a>
 */
public class JsonParser {
    public Object parse(@Language("JSON") String input) throws IOException {
        return parse(new StringReader(input));
    }

    public Object parse(Reader input) throws IOException {
        Object result = null;
        Character ch;

        input.mark(1);
        ch = read(input);

        if (ch != null) {
            if (ch == '"') {
                result = readString(input, '"');
            } else {
                input.reset();
                result = readNumberOrNull(input, null);
            }
        }

        return result;
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

    public Integer readNumberOrNull(Reader input, Character untilChar) throws IOException {
        String string = readString(input, untilChar);
        if (string.equals("null")) {
            return null;
        } else {
            return valueOf(string);
        }
    }

    public Character read(Reader input) throws IOException {
        int read = input.read();
        return read == -1 ? null : (char) read;
    }
}
