package com.codeborne.json;

import java.io.BufferedReader;
import java.io.IOException;

public class StringParser implements Parser {

  @Override
  public boolean couldBe(int character) {
    return character == '"';
  }

  @Override
  public Object parse(BufferedReader reader) throws JsonParseException, IOException {
    StringBuilder builder = new StringBuilder();
    boolean firstQuotation = true;
    boolean isEscaped = false;
    while (true) {
      int character = reader.read();

      if (character == -1) {
        break;
      }

      if (character == '"' && !isEscaped) {
        if (firstQuotation) {
          firstQuotation = false;
        } else {
          break;
        }
      } else if (character == '\\' && !firstQuotation) {
        isEscaped = true;
      } else {
        isEscaped = false;
        builder.append(Character.toString(character));
      }

    }
    return builder.toString();
  }

}
