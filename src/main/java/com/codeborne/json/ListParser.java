package com.codeborne.json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;

public class ListParser implements Parser {
    private static final List<Parser> PARSERS = List.of(new NullParser(), new BooleanParser(), new NumberParser(), new StringParser());

    @Override
    public boolean couldBe(int character) {
        return character == '[';
    }

    @Override
    public Object parse(BufferedReader reader) throws JsonParseException, IOException {
        String value = reader.lines().collect(Collectors.joining());
        if ("[]".equals(value)) return emptyList();
        String replace = value.replace("[", "").replace("]", "").replace(" ", "");
        String[] split = replace.split(",");
        List<Object> list = new ArrayList<>();
        for (String s : split) {
            for (Parser parser : PARSERS) {
                if (parser.couldBe(s.charAt(0))) {
                    list.add(parser.parse(new BufferedReader(new StringReader(s))));
                }
            }
        }
        return list;
    }
}
