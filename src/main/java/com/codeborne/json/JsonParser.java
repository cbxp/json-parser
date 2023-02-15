package com.codeborne.json;

import org.intellij.lang.annotations.Language;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * <a href="https://www.json.org/json-en.html">JSON specification</a>
 */
public class JsonParser {
  public Object parse(@Language("JSON") String input) throws IOException, JsonParseException {
    return parse(new StringReader(input));
  }

  public Object parse(Reader input) throws IOException, JsonParseException {
    int c;
    while ((c = input.read()) != -1) {
      switch (c) {
        case '{':
          return objectParser(input);
        case '[':
           return arrayParser(input);
          // case '"':
        //   return someValue
      }
    }
    return null;
  }
  private ArrayList<Object> arrayParser(Reader input) throws IOException{
    int c;
    ArrayList<Object> currentArray = new ArrayList<Object>();
    while ((c = input.read()) != -1) {
      switch (c) {
        case ']':
          return currentArray;
      }
    }
    throw new IOException("Invalid");
  }

  private Object objectParser(Reader input) throws IOException{
    int c;
    Object currentObject = new Object();
    while ((c = input.read()) != -1) {
      switch (c) {
        case '}':
          return currentObject;
      }
    }
    throw new IOException("Invalid");
  }
}
