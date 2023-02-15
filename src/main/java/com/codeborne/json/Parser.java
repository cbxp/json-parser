package com.codeborne.json;

import java.io.BufferedReader;
import java.io.IOException;

public interface Parser {

  boolean couldBe(int character);

  Object parse(BufferedReader reader) throws JsonParseException, IOException;

}
