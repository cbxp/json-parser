package com.codeborne.json

import com.codeborne.json.assertions.JsonAssertions
import org.junit.jupiter.api.Test

class JsonParserTest {
    private val parser = JsonParser()

    @Test
    fun `plain null`() {
        JsonAssertions.assertThat(parser.parse("null")).isNull()
    }

    @Test
    fun `plain number`() {
        JsonAssertions.assertThat(parser.parse("123")).isEqualTo(123)
    }

    @Test
    fun `plain string`() {
        JsonAssertions.assertThat(parser.parse("\"something\"")).isEqualTo("something")
    }
}
