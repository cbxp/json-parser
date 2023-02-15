package com.codeborne.json;

import org.intellij.lang.annotations.Language;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <a href="https://www.json.org/json-en.html">JSON specification</a>
 */
public class JsonParser {
    public Object parse(@Language("JSON") String input) throws IOException, JsonParseException {
        return parse(new StringReader(input));
    }

    public Object parse(Reader input) throws IOException, JsonParseException {
        // TODO implement me
        StringBuilder contentBuilder = new StringBuilder();
        input.mark(Integer.MAX_VALUE);
        int i = input.read();
        if (i == 91) {
            return parseArray(input, contentBuilder);
        } else if (i == 123) {
            return parseObject(input, contentBuilder);
        }
        input.reset();
        return parseItem(input);
    }

    private Map<Object, Object> parseObject(Reader input, StringBuilder contentBuilder) throws IOException, JsonParseException {
        int i;
        i = input.read();
        while (i != 125) {
            contentBuilder.append((char) i);
            i = input.read();
        }
        Map<Object, Object> map = new HashMap<>();
        String content = contentBuilder.toString();
        String[] entries = content.split(",");
        for (String entry : entries) {
            String[] entryItems = entry.split(":");
            map.put(parseItem(new StringReader(entryItems[0].trim())), parseItem(new StringReader(entryItems[1].trim())));
        }
        return map;
    }

    private List<Object> parseArray(Reader input, StringBuilder contentBuilder) throws IOException {
        int i;
        i = input.read();
        while (i != 93) {
            contentBuilder.append((char) i);
            i = input.read();
        }
        String content = contentBuilder.toString();
        String[] arrayItems = content.split(",");
        return Arrays.stream(arrayItems).map(item -> parseItem(new StringReader(item.trim()))).collect(Collectors.toList());
    }

    private static Object parseItem(Reader input) {
        StringBuilder content = new StringBuilder();
        int i = 0;
        try {
            i = input.read();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        while (i != -1) {
            if (i != 34) {
                content.append((char) i);
            }
            try {
                i = input.read();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if ("null".equals(content.toString())) return null;
        if ("true".equals(content.toString())) return true;
        if ("false".equals(content.toString())) return false;
        try {
            return Integer.valueOf(content.toString());
        } catch (NumberFormatException ignored) {
        }
        try {
            return Double.valueOf(content.toString());
        } catch (NumberFormatException ignored) {
        }
        return content.toString();
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
