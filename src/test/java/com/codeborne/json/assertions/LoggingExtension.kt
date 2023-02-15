package com.codeborne.json.assertions

import org.junit.jupiter.api.extension.AfterEachCallback
import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtensionContext

class LoggingExtension : BeforeEachCallback, AfterEachCallback {
    override fun beforeEach(context: ExtensionContext) {
        log("Before", context)
    }

    override fun afterEach(context: ExtensionContext) {
        log("After", context)
    }

    private fun log(prefix: String, context: ExtensionContext) {
        System.out.printf(
            "%s %s.%s: free mem %s/%s mb %n", prefix,
            context.requiredTestClass.simpleName, context.displayName,
            Runtime.getRuntime().freeMemory() / 1000000,
            Runtime.getRuntime().maxMemory() / 1000000
        )
    }
}
