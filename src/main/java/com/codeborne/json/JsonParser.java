package com.codeborne.json;

import org.intellij.lang.annotations.Language;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.*;

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
            result = readValue(input);
        }

        return result;
    }

    private Map readMap(Reader input) throws IOException {
        HashMap<String, Object> result = new HashMap();
        input.read(); // curly bracket
        readWhitespaces(input);

        Character ch = peek(input);
        if (ch == '}') {
            input.read();
            readWhitespaces(input);
            return result;
        }

        boolean isComma;

        do {
            readWhitespaces(input);
            Pair pair = readKeyAndValue(input);
            result.put(pair.key, pair.value);
            readWhitespaces(input);

            ch = peek(input);

            if (Objects.equals(ch, ',')) {
                isComma = true;
                input.read();
            } else {
                isComma = false;
            }
        } while (isComma);

        input.read(); // end curly
        return result;
    }

    private List<Object> readArray(Reader input) throws IOException {
        input.read(); //square bracket
        ArrayList<Object> result = new ArrayList<>();

        Character ch = peek(input);
        if (ch == ']') {
            input.read();
            return result;
        }

        boolean isComma;

        do {
            readWhitespaces(input);
            Object value = readValue(input);
            result.add(value);
            readWhitespaces(input);

            ch = peek(input);

            if (Objects.equals(ch, ',')) {
                isComma = true;
                input.read();
                readWhitespaces(input);
            } else {
                isComma = false;
            }
        } while (isComma);

        return result;
    }

    private Pair readKeyAndValue(Reader input) throws IOException {
        String key = readString(input);

        readWhitespaces(input);
        input.read(); // colon
        readWhitespaces(input);

        Object value = readValue(input);

        return new Pair(key, value);
    }

    public Object readValue(Reader input) throws IOException {
        Character ch = peek(input);

        if (ch == '"') {
            return readString(input);
        } else if (ch == '{') {
            return readMap(input);
        } else if (ch == '[') {
            return readArray(input);
        } else {
            List<Character> untilChars = List.of('}', ',', ']', '\n', '\r');

            String string = readTokenUntilChar(input, untilChars);
            if (string.equals("null")) {
                return null;
            } else if (string.equals("true")) {
                return true;
            } else if (string.equals("false")) {
                return false;
            } else {
                if (string.contains(".")) {
                    return Double.valueOf(string);
                } else {
                    try {
                        return Integer.valueOf(string);
                    } catch (NumberFormatException e) {
                        return Long.valueOf(string);
                    }
                }
            }
        }
    }

    public String readString(Reader input) throws IOException {
        input.read(); // double quote
        String token = readTokenUntilChar(input, List.of('"'));
        input.read();
        return token;
    }

    private String readTokenUntilChar(Reader input, List<Character> untilChars) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        Character ch;

        ch = peek(input);

        boolean escaping = false;

        while (ch != null && !(untilChars.contains(ch) && !escaping)) {
            escaping = ch == '\\';
            Character character = read(input);

            if (!escaping) stringBuilder.append(character);

            ch = peek(input);
        }

        return stringBuilder.toString();
    }

    private void readWhitespaces(Reader input) throws IOException {
        Character character = peek(input);

        while (character != null && (isWhitespace(character) || character == ' ')) {
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

    static class Pair {
        String key;
        Object value;

        public Pair(String key, Object value) {
            this.key = key;
            this.value = value;
        }
    }
}
