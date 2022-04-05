package com.yasinkacmaz.jetflix.main

import androidx.compose.foundation.layout.Box
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.yasinkacmaz.jetflix.ui.main.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.runner.RunWith
import java.lang.Exception
import java.lang.Thread.sleep

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class MainActivityTest {
    var hiltTestRule = HiltAndroidRule(this)

    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @get:Rule
    var uiTestRule = RuleChain.outerRule(hiltTestRule).around(composeTestRule)

    @Before
    fun init() {
        hiltTestRule.inject()
    }

    @Test
    fun should_render_basic_movies_grid(): Unit = with(composeTestRule) {
        waitUntilTheListIsLoadedSoHacky()

        onNodeWithTag("MoviesGrid", true).assertIsDisplayed()
        onAllNodesWithTag("MovieInfo", true).assertCountEquals(6)
    }

    private fun waitUntilTheListIsLoadedSoHacky() = with(composeTestRule) {
        waitUntil(5000) {
            try {
                onNodeWithTag("MoviesGrid", true).assertIsDisplayed()
                return@waitUntil true
            } catch (t: Throwable) {
                return@waitUntil false

            }
        }
    }
}
