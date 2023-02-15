package com.codeborne.json;

import org.junit.jupiter.api.Test;

import java.util.List;
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

    @Test
    void basicMapWithMultipleKeys() throws Exception {
        assertThat(parser.parse("{\"foo\": 123, \"bar\": \"value\"}"))
                .isEqualTo(Map.of("foo", 123, "bar", "value"));
    }

    @Test
    void basicMapWithNestedKeys() throws Exception {
        assertThat(parser.parse("{\"foo\": {\"bar\":  123}}"))
                .isEqualTo(Map.of("foo", Map.of("bar", 123)));
    }

    @Test
    void basicEmptyArray() throws Exception {
        assertThat(parser.parse("[]"))
                .isEqualTo(List.of());
    }

    @Test
    void basicNumberArray() throws Exception {
        assertThat(parser.parse("[1, 2, 3]"))
                .isEqualTo(List.of(1, 2, 3));
    }

    @Test
    void basicStringArray() throws Exception {
        assertThat(parser.parse("[\"1\", \"2\", \"3\"]"))
                .isEqualTo(List.of("1", "2", "3"));
    }

    @Test
    void basicObjectsArray() throws Exception {
        assertThat(parser.parse("[{\"a\":  1}, {\"b\":  \"2\"}]"))
                .isEqualTo(List.of(Map.of("a", 1), Map.of("b", "2")));
    }
}
