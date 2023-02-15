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
            val text = it.readText().trim()
            when {
                text == "null" -> return null
                text.isInt() -> return text.toInt()
                text.isLong() -> return text.toLong()
                text.isDouble() -> return text.toDouble()
                text.isBoolean() -> return text.toBoolean()
                text.isList() -> return text.parseList()
                text.isObject() -> return text.parseObject()
                else -> return text.unescape()
            }
        }
    }

    private fun String.isInt() = this.toIntOrNull() != null

    private fun String.isLong() = this.toLongOrNull() != null

    private fun String.isDouble() = this.toDoubleOrNull() != null

    private fun String.isBoolean() = this == "false" || this == "true"

    private fun String.isList() = this.startsWith("[") && this.endsWith("]")

    private fun String.parseList(): List<Any?> {
        val listContent = this.replace("[", "").replace("]", "")
        if (listContent.isEmpty()) return listOf()
        return listContent.split(", ").map { parse(it) }
    }

    private fun String.isObject() = this.startsWith("{") && this.endsWith("}")

    private fun String.parseObject(): Map<Any?, Any?> {
        val content = this.replace("{", "").replace("}", "")
        if (content.isEmpty()) return mapOf()
        val entries = content.split(":").map { it.trim() }
        return mapOf(parse(entries[0]) to parse(entries[1]))
    }

    private fun String.unescape() = this
        .unescapeUnicode()
        .unescapeLineBreaks()
        .replace("\"", "")

    private fun String.unescapeUnicode() = replace("\\\\u([0-9A-Fa-f]{4})".toRegex()) {
        String(Character.toChars(it.groupValues[1].toInt(radix = 16)))
    }

    private fun String.unescapeLineBreaks() = this.replace("\\n", "\n").replace("\\r", "\r")
}
