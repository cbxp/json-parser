package com.codeborne.json;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

public class ValueParser {

  public Object parse(BufferedReader reader) throws JsonParseException, IOException {
    reader.mark(1000);
    int character = reader.read();
    reader.reset();

    for (Parser parser : getParsers()) {
      if (parser.couldBe(character)) {
        return parser.parse(reader);
      }
    }
    throw new JsonParseException("Not yet implemented", -1);
  }

  private List<Parser> getParsers() {
    return List.of(new NullParser(), new BooleanParser(), new NumberParser(), new StringParser(), new ListParser());
  }

}
