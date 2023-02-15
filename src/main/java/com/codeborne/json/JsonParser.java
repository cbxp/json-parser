package com.codeborne.json;

import org.intellij.lang.annotations.Language;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
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

    public Object parse(Reader input) throws IOException {
        StringBuffer valueBuffer = new StringBuffer();
        boolean readingValue = false;
        String valueType = "";
        while (true) {
            int character = input.read();
            if (character == -1) { // end of string
                break;
            }
            if (isWhiteSpace(character)) {
                if (readingValue) {
                    if (valueType.equals("string")) {
                        valueBuffer.append((char) character);
                    } else {
                        readingValue = false;
                    }
                }
            } else {
                if (readingValue) {
                    valueBuffer.append((char) character);
                } else {
                    valueType = setValueType(character);
                    readingValue = true;
                    valueBuffer.append((char) character);
                }
            }
        }
//        System.out.println("buffer content " + valueBuffer.toString());
        return convertValueToObject(valueBuffer, valueType);
    }

    @NotNull
    private String setValueType(int character) {
        String valueType;
        if (isStartOfNull(character)) {
            valueType = "null";
        } else if (isStartOfBoolean(character)) {
            valueType = "boolean";
        } else if (isStartOfNumber(character)) {
            valueType = "number";
        } else if (isStartOfString(character)) {
            valueType = "string";
        } else {
            throw new RuntimeException("unknown value start");
        }
        return valueType;
    }

    @Nullable
    private static Object convertValueToObject(StringBuffer valueBuffer, String valueType) {
        switch (valueType) {
            case "null":
                return null;
            case "boolean":
                return Boolean.parseBoolean(valueBuffer.toString());
            case "number":
                return Integer.parseInt(valueBuffer.toString());
            case "string":
                return valueBuffer.toString().substring(1, valueBuffer.length() - 2);
            default:
                throw new RuntimeException("not yet implemented");
        }
    }

    private boolean isStartOfString(int character) {
        String s = String.valueOf((char) character);
        return s.equals("\"");
    }

    private boolean isStartOfNumber(int character) {
        String s = String.valueOf((char) character);
        return s.equalsIgnoreCase("-") || s.matches("\\d");
    }

    private boolean isStartOfBoolean(int character) {
        String s = String.valueOf((char) character);
        return s.equalsIgnoreCase("t") || s.equalsIgnoreCase("f");
    }

    private boolean isWhiteSpace(int character) {
        return (char) character == ' ';
    }

    private boolean isStartOfNull(int character) {
        return (char) character == 'n';
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
