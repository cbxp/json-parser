package com.codeborne.json.assertions;

import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class LoggingExtension implements BeforeEachCallback, AfterEachCallback {
  @Override
  public void beforeEach(ExtensionContext context) {
    log("Before", context);
  }

  @Override
  public void afterEach(ExtensionContext context) {
    log("After", context);
  }

  private void log(String prefix, ExtensionContext context) {
    System.out.printf("%s %s.%s: free mem %s/%s mb %n", prefix,
      context.getRequiredTestClass().getSimpleName(), context.getDisplayName(),
      Runtime.getRuntime().freeMemory() / 1_000_000,
      Runtime.getRuntime().maxMemory() / 1_000_000
    );
  }
}
