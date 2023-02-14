package com.codeborne.json;

import org.jetbrains.annotations.NotNull;

import java.io.Reader;

class ContentGenerator extends Reader {
  private final int contentLength;
  private final String unit;
  private int bytesCount = 0;
  private boolean finished = false;

  ContentGenerator(String unit, int contentLength) {
    this.unit = "," + unit;
    this.contentLength = contentLength;
  }

  @Override
  public int read() {
    if (finished) {
      return -1;
    }
    else if (bytesCount == 0) {
      bytesCount++;
      return '[';
    }
    else if (bytesCount >= contentLength && bytesCount % unit.length() == 0) {
      finished = true;
      return ']';
    }
    else {
      return unit.charAt(bytesCount++ % unit.length());
    }
  }

  @Override
  public int read(@NotNull char[] buffer, int off, int len) {
    int count = 0;
    for (int i = off; i < off + len; i++) {
      int c = read();
      if (c == -1) break;
      buffer[i] = (char) c;
      count++;
    }
    return count;
  }

  @Override
  public void reset() {
    finished = false;
    bytesCount = 0;
  }

  @Override
  public void close() {
  }
}
