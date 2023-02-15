package com.codeborne.json;

import org.intellij.lang.annotations.Language;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * <a href="https://www.json.org/json-en.html">JSON specification</a>
 */
public class JsonParser {
    public Object parse(@Language("JSON") String input) throws IOException, JsonParseException {
        return parse(new StringReader(input));
    }

    // switch to decide value type (and start reading)
    // switch to end value reading based on type
    // switch to convert read string to type
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
        } else if (isStartOfArray(character)) {
            valueType = "array";
        } else if (isStartOfObject(character)) {
            valueType = "object";
        } else {
            throw new RuntimeException("unknown value start");
        }
        return valueType;
    }

    private boolean isStartOfObject(int character) {
        String s = String.valueOf((char) character);
        return s.equalsIgnoreCase("{");
    }

    private boolean isStartOfArray(int character) {
        String s = String.valueOf((char) character);
        return s.equalsIgnoreCase("[");
    }

    @Nullable
    private Object convertValueToObject(StringBuffer valueBuffer, String valueType) {
        switch (valueType) {
            case "null":
                return null;
            case "boolean":
                return Boolean.parseBoolean(valueBuffer.toString());
            case "number":
                return numberValue(valueBuffer.toString());
            case "string":
                return valueBuffer.toString().substring(1, valueBuffer.length() - 2);
            case "array":
                return List.of();
            case "object":
                return Map.of();
            default:
                throw new RuntimeException("not yet implemented");
        }
    }

    private Object numberValue(String numberString) {
        BigDecimal bigDecimal = new BigDecimal(numberString);
        if (isWholeNumber(bigDecimal)) {
            if (bigDecimal.compareTo(new BigDecimal(Long.MAX_VALUE)) >= 0) {
                return bigDecimal;
            } else if (bigDecimal.compareTo(new BigDecimal(Integer.MAX_VALUE)) < 0) {
                return bigDecimal.intValue();
            } else {
                return bigDecimal.longValue();
            }
        } else {
            return bigDecimal.doubleValue();
        }
    }

    public boolean isWholeNumber(BigDecimal number) {
        return number.remainder(BigDecimal.ONE).compareTo(BigDecimal.ZERO) == 0;
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
