package com.codeborne.json;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ObjectParser implements Parser {

  private StringParser stringParser = new StringParser();
  private NumberParser numberParser = new NumberParser();
  private List<Parser> parsers = List.of(stringParser, numberParser);

  @Override public boolean couldBe(int character) {
    return character == '{';
  }

  @Override public Object parse(BufferedReader reader) throws JsonParseException, IOException {
    Map<String, Object> objectMap = new HashMap<>();

    boolean mappingKey = true;
    boolean mappingValue = false;
    String currentKey = null;
    while (true) {
      int character = reader.read();
      if (character == '}' || character == -1) {
        break;
      }
      if (isWhitespace(character) || isColon(character) || isComma(character)) {
        reader.mark(1000);
        continue;
      }

      if (stringParser.couldBe(character) && mappingKey) {
        mappingKey = false;
        reader.reset();
        currentKey = stringParser.parse(reader).toString();
        mappingValue = true;
      } else if (mappingValue) {
        mappingValue = false;
        for (Parser parser: parsers) {
          if (parser.couldBe(character)) {
            reader.reset();
            objectMap.put(Objects.requireNonNull(currentKey), parser.parse(reader));
          }
        }
        mappingKey = true;
      }

      reader.mark(1000);
    }

    return objectMap;
  }

  private boolean isComma(int character) {
    return character == ',';
  }

  private boolean isColon(int character) {
    return character == ':';
  }

  private boolean isWhitespace(int chr) {
    return Character.isWhitespace(chr);
  }
}
