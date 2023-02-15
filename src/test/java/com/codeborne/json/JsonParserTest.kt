package com.codeborne.json

import com.codeborne.json.assertions.JsonAssertions
import org.junit.jupiter.api.Test

class JsonParserTest {
    private val parser = JsonParser()
    
    @Test
    @Throws(Exception::class)
    fun plainNull() {
        JsonAssertions.assertThat(parser.parse("null")).isNull()
    }
}
