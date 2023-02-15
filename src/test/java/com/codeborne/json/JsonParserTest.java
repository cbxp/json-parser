package com.codeborne.json;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;
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
    void jsonWithString() throws IOException, JsonParseException {
        assertThat(parser.parse("\"string\"")).isEqualTo("string");
    }

    @Test
    void jsonWithNumber() throws IOException, JsonParseException {
        assertThat(parser.parse("9")).isEqualTo(9);
    }
    @Test
    void jsonWithDecimalNumber() throws IOException, JsonParseException {
        assertThat(parser.parse("9.9")).isEqualTo(9.9);
    }
    @Test
    void jsonWithArray() throws IOException, JsonParseException {
        assertThat(parser.parse("[\"string\",\"string2\"]")).isEqualTo(List.of("string","string2"));
    }
    @Test
    void jsonWithOneFieldObject() throws IOException, JsonParseException {
        assertThat(parser.parse("{\"string\": \"stringValue\"}")).isEqualTo(Map.of("string", "stringValue"));
    }

    @Test
    void jsonWithMultipleFieldObject() throws IOException, JsonParseException {
        assertThat(parser.parse("{\"string\": \"stringValue\", \"number\" : 10.10}")).isEqualTo(Map.of("string", "stringValue", "number", 10.10));
    }
    @Test
    void jsonWithObjectInsideOfAnObject() throws IOException, JsonParseException {
        assertThat(parser.parse("{\"string\":{\"stringKey\": \"stringValue\"}}")).isEqualTo(Map.of("string", Map.of("stringKey", "stringValue")));
    }
    @Test
    @Disabled
    void jsonWithArrayOfObjects() throws IOException, JsonParseException {
        assertThat(parser.parse("[{\"string\": \"stringValue\"},{\"string\": \"stringValue2\"}]")).isEqualTo(List.of(Map.of("string", "stringValue"),Map.of("string", "stringValue2")));
    }
}
