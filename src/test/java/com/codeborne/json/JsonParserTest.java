package com.codeborne.json;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static com.codeborne.json.assertions.JsonAssertions.assertThat;

class JsonParserTest {
    private final JsonParser parser = new JsonParser();

    @Test
    void plainNull() throws Exception {
        assertThat(parser.parse("null")).isNull();
    }

    @Test
    void basicStringField() throws Exception {
        assertThat(parser.parse("\"field\"")).isEqualTo("field");
    }

    @Test
    void objectOfString() {
        // language=JSON
        String test = """
        {"key": "value"}
        """;
        assertThat(test).isEqualTo(Map.of("key", "value"));
    }

    @Test
    void objectOfInteger() {
        // language=JSON
        String test = """
        {"key": 123}
        """;
        assertThat(test).isEqualTo(Map.of("key", 123));
    }

    @Test
    void basicStringNumber() throws Exception {
        assertThat(parser.parse("2")).isEqualTo(2);
    }
}
