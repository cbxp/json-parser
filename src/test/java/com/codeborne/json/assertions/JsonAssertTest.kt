package com.codeborne.json.assertions

import org.junit.jupiter.api.Test
import java.util.Map

internal class JsonAssertTest {
    @Test
    fun canExtractElementsFromMap() {
        JsonAssertions.assertThat(Map.of("user", Map.of("name", "me")))
            .extractingFromJson("user", "name").isEqualTo("me")
    }
}
