package com.yasinkacmaz.jetflix.fake.rule

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import java.io.BufferedReader
import java.lang.IllegalStateException

class FakeWebServerRule : TestRule {

    internal val fakeWebServer = MockWebServer()

    override fun apply(base: Statement, description: Description): Statement {
        return object : Statement() {
            override fun evaluate() {
                init()

                try {
                    base.evaluate()
                } finally {
                    performMinimalPostTestCleanup()
                }
            }
        }
    }

    fun init() {
        fakeWebServer.start(8080)
    }

    private fun performMinimalPostTestCleanup() {
        // Shutting down the fake server at the end of the tests
        fakeWebServer.shutdown()
    }
}