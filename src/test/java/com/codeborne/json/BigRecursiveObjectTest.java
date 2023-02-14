package com.codeborne.json;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static com.codeborne.json.JsonAssertions.file;
import static org.assertj.core.api.Assertions.assertThat;


public class BigRecursiveObjectTest {
  private final JsonParser parser = new JsonParser();

  @Test
  @SuppressWarnings("unchecked")
  void hugeJson() throws IOException {
    Object json = parser.parse(file("big-recursive-object.json"));
    for (int i = 0; i < 4932; i++) {
      assertThat(json).isInstanceOf(Map.class);
      Map<String, ?> map = (Map<String, ?>) json;
      assertThat(map).hasSize(1);
      assertThat(map).containsKey("a");
      json = map.get("a");
    }
  }

}
