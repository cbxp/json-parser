package com.codeborne.json;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static com.codeborne.json.assertions.JsonAssertions.assertThat;
import static java.math.BigDecimal.ZERO;
import static java.math.BigDecimal.valueOf;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class JsonParserTest {
    private final JsonParser parser = new JsonParser();

    @Test
    void simpleValues() throws IOException, JsonParseException {
        assertThat(parser.parse("null")).isNull();
        assertThat(parser.parse("false")).isEqualTo(false);
        assertThat(parser.parse("true")).isEqualTo(true);
    }

    @Test
    void simpleValuesWithWhitespace() throws IOException, JsonParseException {
        assertThat(parser.parse("null  ")).isNull();
        assertThat(parser.parse("null  ")).isNull();
        assertThat(parser.parse("\tfalse")).isEqualTo(false);
        assertThat(parser.parse("\ttrue   ")).isEqualTo(true);
        assertThat(parser.parse("\ntrue   ")).isEqualTo(true);
        assertThat(parser.parse("\r\ntrue   ")).isEqualTo(true);
        assertThat(parser.parse("\r\ntrue \n  ")).isEqualTo(true);
    }

    @Test
    void numbers() throws IOException, JsonParseException {
        assertThat(parser.parse("0")).isEqualTo(valueOf(0));
        assertThat(parser.parse("7")).isEqualTo(valueOf(7));
        assertThat(parser.parse("7.1")).isEqualTo(new BigDecimal("7.1"));
        assertThat(parser.parse("-13.13")).isEqualTo(new BigDecimal("-13.13"));
        assertThat(parser.parse("1e3")).isEqualTo(new BigDecimal("1E+3"));
        assertThat(parser.parse("1e-2")).isEqualTo(new BigDecimal("1E-2"));
        assertThat(parser.parse("1E+3")).isEqualTo(new BigDecimal("1E+3"));
    }

    @Test
    void invalidNumbers() throws IOException, JsonParseException {
        assertThatThrownBy(() -> parser.parse("1X2")).isInstanceOf(JsonParseException.class);
        assertThatThrownBy(() -> parser.parse("x1")).isInstanceOf(JsonParseException.class);
        assertThatThrownBy(() -> parser.parse("0xABCD")).isInstanceOf(JsonParseException.class);
    }

    @Test
    void simpleString() throws IOException, JsonParseException {
        assertThat(parser.parse(quoted(""))).isEqualTo("");
        assertThat(parser.parse(quoted("ab cd"))).isEqualTo("ab cd");
        assertThat(parser.parse(quoted("tääk"))).isEqualTo("tääk");
    }

    @Test
    void stringsWithEscapedSymbols() throws IOException, JsonParseException {
        assertThat(parser.parse(quoted("\\\\"))).isEqualTo("\\");
        assertThat(parser.parse(quoted("\\/"))).isEqualTo("/");
        assertThat(parser.parse(quoted("\\\""))).isEqualTo("\"");
        assertThat(parser.parse(quoted("\\b"))).isEqualTo("\b");
        assertThat(parser.parse(quoted("\\f"))).isEqualTo("\f");
        assertThat(parser.parse(quoted("\\r"))).isEqualTo("\r");
        assertThat(parser.parse(quoted("\\n"))).isEqualTo("\n");
        assertThat(parser.parse(quoted("\\t"))).isEqualTo("\t");
    }

    @Test
    void unicodeCharacters() throws IOException, JsonParseException {
        assertThat(parser.parse(quoted("\\u1234"))).isEqualTo("\u1234");
        assertThat(parser.parse(quoted("\\uabcd"))).isEqualTo("\uabcd");
        assertThat(parser.parse(quoted("abcd\\uabcd"))).isEqualTo("abcd\uabcd");
        assertThat(parser.parse(quoted("t\\u00E4\\u00E4k"))).isEqualTo("tääk");
    }

    @Test
    void invalidString() throws IOException, JsonParseException {
        assertThatThrownBy(() -> parser.parse("\"a")).isInstanceOf(JsonParseException.class);
        assertThatThrownBy(() -> parser.parse(quoted("ab\"cd"))).isInstanceOf(JsonParseException.class);
        assertThatThrownBy(() -> parser.parse(quoted("\\uabcX"))).isInstanceOf(JsonParseException.class);
    }

    @Test
    void singleElementArray() throws IOException, JsonParseException {
        assertThat(parser.parse("[0]")).isEqualTo(List.of(ZERO));
        assertThat(parser.parse("[\"a\"]")).isEqualTo(List.of("a"));
        assertThat(parser.parse("[true]")).isEqualTo(List.of(true));
    }

    @Test
    void array() throws IOException, JsonParseException {
        assertThat(parser.parse("[0, 5, 9]")).isEqualTo(List.of(valueOf(0), valueOf(5), valueOf(9)));
        assertThat(parser.parse("[\"a\", 5, true]")).isEqualTo(List.of("a", valueOf(5), true));
    }

    @Test
    void object() throws IOException, JsonParseException {
        assertThat(parser.parse("{}")).isEqualTo(Map.of());
        assertThat(parser.parse("{ }")).isEqualTo(Map.of());
        assertThat(parser.parse("{\t}")).isEqualTo(Map.of());
        assertThat(parser.parse("{\n}")).isEqualTo(Map.of());
        assertThat(parser.parse("{\"1\": 2}")).isEqualTo(Map.of("1", valueOf(2)));
        assertThat(parser.parse("{\"a\": \"b\"}")).isEqualTo(Map.of("a", "b"));
        assertThat(parser.parse("{ \"a\" : \"b\" }")).isEqualTo(Map.of("a", "b"));
        assertThat(parser.parse("""
                {
                  "a": "b",
                  "c": "d"
                }
                """)).isEqualTo(Map.of("a", "b", "c", "d"));
        assertThat(parser.parse("""
                {
                  "a,b": "b"
                }
                """)).isEqualTo(Map.of("a,b", "b"));
        assertThat(parser.parse("""
                {
                  "a:b": "b"
                }
                """)).isEqualTo(Map.of("a:b", "b"));
        assertThat(parser.parse("""
                {
                  "a": "b:c"
                }
                """)).isEqualTo(Map.of("a", "b:c"));
        assertThat(parser.parse("""
                {
                  "a": ":c"
                }
                """)).isEqualTo(Map.of("a", ":c"));
        assertThat(parser.parse("""
                {
                  ":a": ":c"
                }
                """)).isEqualTo(Map.of(":a", ":c"));
    }

    @Test
    void invalidObject() {
        assertThatThrownBy(() -> parser.parse("""
                    {
                      "a": "b",
                    }
                """)).isInstanceOf(JsonParseException.class);
        assertThatThrownBy(() -> parser.parse("""
                    {
                      "a":
                    }
                """)).isInstanceOf(JsonParseException.class);
        assertThatThrownBy(() -> parser.parse("""
                    {
                      1:1
                    }
                """)).isInstanceOf(JsonParseException.class);
    }

    @Test
    void nested() throws IOException, JsonParseException {
        assertThat(parser.parse("""
                {
                "a": {
                "b": "c",
                "d": [1, true]
                }
                }
                """)).isEqualTo(Map.of("a", Map.of("b", "c", "d", List.of(valueOf(1), true))));
    }

    @NotNull
    private static String quoted(String value) {
        return "\"" + value + "\"";
    }
}
