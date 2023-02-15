package com.codeborne.json

import com.codeborne.json.assertions.JsonAssertions.assertThat
import com.codeborne.json.assertions.JsonAssertions.file
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class MoreJsonTest {
    private val parser = JsonParser()

    @Test
    fun bigDecimal() {
        assertThat(parser.parse("123.456789")).isEqualTo(BigDecimal("123.456789"))
    }

    @Test
    fun emptyInput() {
        assertThatThrownBy { parser.parse(file("empty.json")) }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("Invalid json: \"\"")
    }

    @Test
    fun sample1() {
        val json = parser.parse(file("sample1.json"))
        assertThat(json).extractingFromJson("clinical_study", "last_update_submitted_qc")
            .isEqualTo("September 10, 2019")
    }

    @Test
    fun sample2() {
        val json = parser.parse(file("sample2.json"))
        assertThat(json).extractingFromJson("success").isEqualTo(true)
        assertThat(json).extractingFromJson("error").isNull()
        assertThat(json).extractingFromJson("response", 0, "interval").isEqualTo("day")
        assertThat(json).extractingFromJson("response", 0, "periods", 0, "timestamp").isEqualTo(1665032400)
    }

    @Test
    fun sample3() {
        val json = parser.parse(file("sample3.json"))
        assertThat(json).extractingFromJson("name", "first").isEqualTo("Tom")
        assertThat(json).extractingFromJson("name", "last").isEqualTo("La'Cruise")
        assertThat(json).extractingFromJson("age").isEqualTo(56)
        assertThat(json).extractingFromJson("weight").isEqualTo(BigDecimal("67.5"))
        assertThat(json).extractingFromJson("wife").isNull()
        assertThat(json).extractingFromJson("hasChildren").isEqualTo(true)
        assertThat(json).extractingFromJson("Born At").isEqualTo("Syracuse, NY")
        assertThat(json).extractingFromJson("photo").isEqualTo("https://jsonformatter.org/img/tom-cruise.jpg")
    }

    @Test
    fun floats() {
        val json = parser.parse(file("floats-755.json")) as List<*>
        assertThat(json[0]).isEqualTo(BigDecimal("7.038531E-26"))
        assertThat(json[1]).isEqualTo(BigDecimal("1.199999988079071"))
        assertThat(json[1003]).isEqualTo(BigDecimal("-0.002195333"))
    }
}
