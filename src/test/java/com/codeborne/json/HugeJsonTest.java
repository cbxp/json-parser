package com.codeborne.json;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class HugeJsonTest {
  private final JsonParser parser = new JsonParser();

  @Test
  @SuppressWarnings("unchecked")
  void hugeJson() throws IOException {
    Object json = parser.parse(new BufferedReader(new ContentGenerator("\"Türi\\\"Jõri\"", 3_000_000)));
    assertThat(json).isInstanceOf(List.class);
    assertThat((List<?>) json).hasSize(230770);
    for (String listElement : (List<String>) json) {
      assertThat(listElement).isEqualTo("Türi\"Jõri");
    }
  }

}
