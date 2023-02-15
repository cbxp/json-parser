package com.codeborne.json

import org.intellij.lang.annotations.Language
import java.io.Reader
import java.io.StringReader

/**
 * [JSON specification](https://www.json.org/json-en.html)
 */
class JsonParser {
    fun parse(@Language("JSON") input: String?): Any? {
        return parse(StringReader(input))
    }

    fun parse(input: Reader?): Any? {
        return input?.let {
            val text = it.readText()
            if (text == "null") return null
            if (text.isNumeric()) return text.toInt()
            if (text.isBoolean()) return text.toBoolean()
            return text.replace("\"", "")
        }
    }

    private fun String.isNumeric() = this.all {
        it.isDigit()
    }

    private fun String.isBoolean() = this == "false" || this == "true"
}
