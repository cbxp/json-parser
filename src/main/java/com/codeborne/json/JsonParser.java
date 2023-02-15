package com.codeborne.json;

import org.intellij.lang.annotations.Language;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <a href="https://www.json.org/json-en.html">JSON specification</a>
 */
public class JsonParser {
    public Object parse(@Language("JSON") String input) throws IOException, JsonParseException {
        String value = input.strip();
        if (value.startsWith("[")) {
            return array(value);
        }
        if (value.startsWith("{")) {
            return object(value);
        }
        return switch (value) {
            case "null" -> null;
            case "false" -> false;
            case "true" -> true;
            default -> stringOrNumber(value);
        };
    }

    private Object object(String value) throws JsonParseException, IOException {
        Map<String, Object> result = new HashMap<>();
        String content = value.substring(1, value.length() - 1).strip();
        if (!content.isEmpty()) {
            String[] pairs = content.split(",\\s*\"");
            boolean firstPair = true;
            for (String pair : pairs) {
                if (firstPair) pair = pair.strip().substring(1);
                String[] nameAndValue = pair.split("\"\\s*:", 2);
                if (nameAndValue.length != 2) throw new JsonParseException("", 0);
                String name = nameAndValue[0];
                name = "\"" + name;
                name += "\"";
                if (!isString(name)) throw new JsonParseException("", 0);
                result.put(string(name), parse(nameAndValue[1]));
                firstPair = false;
            }
        }
        return result;
    }

    private Object array(String value) throws IOException, JsonParseException {
        List<Object> result = new ArrayList<>();
        String[] elements = value.substring(1, value.length() - 1).split(",");
        for (String element : elements) {
            result.add(parse(element));
        }
        return result;
    }

    private Object stringOrNumber(String value) throws JsonParseException {
        if (isString(value)) {
            return string(value);
        }
        return number(value);
    }

    private boolean isString(String value) {
        return value.startsWith("\"") && value.endsWith("\"");
    }

    private String string(String value) throws JsonParseException {
        String stripped = value.substring(1, value.length() - 1);
        if (stripped.contains("\"") && !stripped.contains("\\\"")) {
            throw new JsonParseException("", 0);
        }
        stripped = handleUnicode(stripped);
        return stripped
                .replace("\\\\", "\\")
                .replace("\\\"", "\"")
                .replace("\\/", "/")
                .replace("\\b", "\b")
                .replace("\\f", "\f")
                .replace("\\r", "\r")
                .replace("\\n", "\n")
                .replace("\\t", "\t")
                ;
    }

    private static String handleUnicode(String value) throws JsonParseException {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < value.length(); i++) {
            if (i < value.length() - 1 && value.charAt(i) == '\\' && value.charAt(i + 1) == 'u') {
                try {
                    result.append(Character.toString(Integer.parseInt(value.substring(i + 2, i + 6), 16)));
                    i += 5;
                } catch (NumberFormatException e) {
                    throw new JsonParseException("", 0);
                }
            } else {
                result.append(value.charAt(i));
            }
        }
        return result.toString();
    }

    private static BigDecimal number(String value) throws JsonParseException {
        try {
            return new BigDecimal(value);
        } catch (NumberFormatException e) {
            throw new JsonParseException(e.getMessage(), 0);
        }
    }

    public Object parse(Reader input) throws IOException, JsonParseException {
        return null;
    }
}
