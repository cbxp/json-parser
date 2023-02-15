package com.codeborne.json

import com.codeborne.json.assertions.JsonAssertions
import org.junit.jupiter.api.Test

class JsonParserTest {
    private val parser = JsonParser()

    @Test
    @Throws(Exception::class)
    fun `plain null`() {
        JsonAssertions.assertThat(parser.parse("null")).isNull()
    }

    @Test
    fun `plain number`() {
        JsonAssertions.assertThat(parser.parse(123)).isEqualTo(123)
    }
}
