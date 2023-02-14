package com.codeborne.json;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static com.codeborne.json.JsonAssertions.file;


public class BigRecursiveArrayTest {
  private final JsonParser parser = new JsonParser();

  @Test
  void hugeJson() throws IOException {
    Object json = parser.parse(file("big-recursive-array.json"));
    for (int i = 0; i < 4932; i++) {
      assertThat(json).isInstanceOf(List.class);
      List<?> list = (List<?>) json;
      assertThat(list).hasSize(2);
      assertThat(list.get(0)).isEqualTo("a");
      json = list.get(1);
    }
  }

}
