package com.yasinkacmaz.jetflix.fake.main

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.yasinkacmaz.jetflix.di.RetrofitModule
import com.yasinkacmaz.jetflix.fake.constants.FakeResponses
import com.yasinkacmaz.jetflix.fake.rule.FakeWebServerRule
import com.yasinkacmaz.jetflix.ui.main.MainActivity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.runner.RunWith
import retrofit2.Retrofit
import javax.inject.Singleton

@RunWith(AndroidJUnit4::class)
@UninstallModules(RetrofitModule::class)
@HiltAndroidTest
class MainActivityFakeNetworkTest {

    @Module
    @InstallIn(SingletonComponent::class)
    object FakeRetrofitModule {

        @OptIn(ExperimentalSerializationApi::class)
        @Provides
        @Singleton
        fun provideRetrofit(okHttpClient: OkHttpClient, json: Json): Retrofit {
            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("http://localhost:8080")
                .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
                .build()
        }
    }

    @get:Rule
    var fakeWebServerRule = FakeWebServerRule()

    var hiltTestRule = HiltAndroidRule(this)
    val composeTestRule = createAndroidComposeRule<MainActivity>()
    @get:Rule
    var uiTestRule = RuleChain.outerRule(hiltTestRule).around(composeTestRule)

    @Before
    fun init() {
        hiltTestRule.inject()

        fakeWebServerRule.fakeWebServer.enqueue(
            MockResponse().setResponseCode(200).setBody(FakeResponses.genres)
        )
        fakeWebServerRule.fakeWebServer.enqueue(
            MockResponse().setResponseCode(200).setBody(FakeResponses.movies)
        )
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
