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

    @Throws(IOException::class, JsonParseException::class)
    fun parse(@Language("JSON") input: Int?): Any? {
        return input
    }

    @Throws(IOException::class, JsonParseException::class)
    fun parse(input: Reader?): Any? {
        return input?.let {
            val text = it.readText()
            if (text == "null") return null
            text.toBoolean()
        }
    }
}
