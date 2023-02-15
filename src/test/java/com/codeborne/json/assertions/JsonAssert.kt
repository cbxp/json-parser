package com.codeborne.json.assertions

import org.assertj.core.api.AbstractObjectAssert
import org.assertj.core.api.Assertions

class JsonAssert(json: Any?) : AbstractObjectAssert<JsonAssert?, Any?>(json, JsonAssert::class.java) {
    fun isListOfLength(expectedLength: Int): JsonAssert {
        isNotNull()
        Assertions.assertThat(actual as List<*>?).hasSize(expectedLength)
        return this
    }

    fun extractingFromJson(vararg jsonPath: Any?): JsonAssert {
        isNotNull()
        var value = actual
        for (step in jsonPath) {
            value = if (step is String) {
                getFromMap(value, step)
            } else if (step is Int) {
                getFromList(value, step)
            } else {
                throw IllegalArgumentException("Json path element can be String or Integer")
            }
        }
        return JsonAssert(value)
    }

    companion object {
        private fun getFromMap(value: Any?, step: String): Any? {
            require(value is Map<*, *>) { String.format("Cannot extract json element %s from %s", step, value) }
            val jsonObject = value as Map<String, Any>
            return jsonObject[step]
        }

        private fun getFromList(value: Any?, step: Int): Any {
            require(value is List<*>) { String.format("Cannot extract json element %s from %s", step, value) }
            return value[step]!!
        }
    }
}
