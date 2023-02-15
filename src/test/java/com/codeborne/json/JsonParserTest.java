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
    void basicStringNumber() throws Exception {
        assertThat(parser.parse("2")).isEqualTo(2);
    }

    @Test
    void basicMapWithField() throws Exception {
        assertThat(parser.parse("{\"field\": \"value\"}")).isEqualTo(Map.of("field", "value"));
    }

    @Test
    void basicMapWithNumberField() throws Exception {
        assertThat(parser.parse("{\"field\": 123}")).isEqualTo(Map.of("field", 123));
    }

    @Test
    void basicMapWithBooleanField() throws Exception {
        assertThat(parser.parse("{\"field\": false}")).isEqualTo(Map.of("field", false));
    }

    @Test
    void basicMapWithEmptyMap() throws Exception {
        assertThat(parser.parse("{}"))
                .isEqualTo(Map.of());
    }
}
