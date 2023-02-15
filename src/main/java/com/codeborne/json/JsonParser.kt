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
            text.toBoolean()
        }
    }

    private fun String.isNumeric() = this.all {
        it.isDigit()
    }
}
