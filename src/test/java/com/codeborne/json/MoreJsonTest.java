package com.codeborne.json;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import static com.codeborne.json.assertions.JsonAssertions.assertThat;
import static com.codeborne.json.assertions.JsonAssertions.file;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class MoreJsonTest {
  private final JsonParser parser = new JsonParser();

  @Test
  void bigDecimal() throws IOException, JsonParseException {
    assertThat(parser.parse("123.456789")).isEqualTo(new BigDecimal("123.456789"));
  }

  @Test
  void emptyInput() {
    assertThatThrownBy(() -> parser.parse(file("empty.json")))
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessage("Invalid json: \"\"");
  }


  @Test
  void sample1() throws IOException, JsonParseException {
    Object json = parser.parse(file("sample1.json"));
    assertThat(json).extractingFromJson("clinical_study", "last_update_submitted_qc")
      .isEqualTo("September 10, 2019");
  }

  @Test
  void sample2() throws IOException, JsonParseException {
    Object json = parser.parse(file("sample2.json"));
    assertThat(json).extractingFromJson("success").isEqualTo(true);
    assertThat(json).extractingFromJson("error").isNull();
    assertThat(json).extractingFromJson("response", 0, "interval").isEqualTo("day");
    assertThat(json).extractingFromJson("response", 0, "periods", 0, "timestamp").isEqualTo(1665032400);
  }

  @Test
  void sample3() throws IOException, JsonParseException {
    Object json = parser.parse(file("sample3.json"));
    assertThat(json).extractingFromJson("name", "first").isEqualTo("Tom");
    assertThat(json).extractingFromJson("name", "last").isEqualTo("La'Cruise");
    assertThat(json).extractingFromJson("age").isEqualTo(56);
    assertThat(json).extractingFromJson("weight").isEqualTo(new BigDecimal("67.5"));
    assertThat(json).extractingFromJson("wife").isNull();
    assertThat(json).extractingFromJson("hasChildren").isEqualTo(true);
    assertThat(json).extractingFromJson("Born At").isEqualTo("Syracuse, NY");
    assertThat(json).extractingFromJson("photo").isEqualTo("https://jsonformatter.org/img/tom-cruise.jpg");
  }

  @Test
  void floats() throws IOException, JsonParseException {
    Object json = parser.parse(file("floats-755.json"));
    assertThat(json).isInstanceOf(List.class);
    List<?> list = (List<?>) json;
    assertThat(list.get(0)).isEqualTo(7.038531E-26);
    assertThat(list.get(1)).isEqualTo(1.199999988079071);
    assertThat(list.get(1003)).isEqualTo(-0.002195333);
  }

}
