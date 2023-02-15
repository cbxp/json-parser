package com.codeborne.json.assertions

import java.io.InputStreamReader
import java.io.Reader
import java.nio.charset.StandardCharsets

object JsonAssertions {
    @JvmStatic
    fun assertThat(parsedJson: Any?): JsonAssert {
        return JsonAssert(parsedJson)
    }

    @JvmStatic
    fun file(fileName: String): Reader {
        return InputStreamReader(JsonAssertions::class.java.getResourceAsStream("/$fileName"), StandardCharsets.UTF_8)
    }
}
