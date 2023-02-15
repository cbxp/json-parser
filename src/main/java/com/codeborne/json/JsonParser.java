package com.codeborne.json;

import org.intellij.lang.annotations.Language;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.List;
import java.util.stream.Stream;

/**
 * <a href="https://www.json.org/json-en.html">JSON specification</a>
 */
public class JsonParser {
    public Object parse(@Language("JSON") String input) throws IOException, JsonParseException {
        return parse(new StringReader(input));
    }

    public Object parse(Reader input) throws IOException, JsonParseException {
        try (var br = new BufferedReader(input)) {
            String line = br.readLine();
            if (line != null) {
                switch (line) {
                    case "true":
                        return true;
                    case "false":
                        return false;
                    case "null" :
                        return null;
                }
                if (checkIfInteger(line)) return Integer.valueOf(line);
            }
        }
        return null;
    }

    private boolean checkIfInteger(String line) {
        if (line.length() > 1) {
            if (!line.substring(0, 1).matches("[0-9, -]")) return false;
            return !line.substring(1).matches("[0-9]");
        } else return !line.substring(1).matches("[0-9]");
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
