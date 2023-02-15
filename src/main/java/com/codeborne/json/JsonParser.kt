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
            when {
                text == "null" -> return null
                text.isInt() -> return text.toInt()
                text.isLong() -> return text.toLong()
                text.isDouble() -> return text.toDouble()
                text.isBoolean() -> return text.toBoolean()
                text == "{}" -> return mapOf<String, String>()
                text == "[]" -> return listOf<String>()
                else -> return text.replace("\"", "")
            }
        }
    }

    private fun String.isInt() = this.toIntOrNull() != null

    private fun String.isLong() = this.toLongOrNull() != null

    private fun String.isDouble() = this.toDoubleOrNull() != null

    private fun String.isBoolean() = this == "false" || this == "true"
}
