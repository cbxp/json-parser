package com.codeborne.json;

import java.text.ParseException;

public class JsonParseException extends ParseException {
  public JsonParseException(String message, int errorOffset) {
    super(message, errorOffset);
  }
}
