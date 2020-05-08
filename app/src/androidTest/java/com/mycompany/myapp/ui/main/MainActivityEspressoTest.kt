package com.mycompany.myapp.ui.main

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.clearText
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isEnabled
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.mycompany.myapp.EspressoMatchers.regex
import com.mycompany.myapp.R
import com.mycompany.myapp.data.api.github.GitHubInteractor
import com.mycompany.myapp.data.api.github.GitHubInteractor.LoadCommitsRequest
import com.mycompany.myapp.data.api.github.GitHubInteractor.LoadCommitsResponse
import com.mycompany.myapp.data.api.github.model.Author
import com.mycompany.myapp.data.api.github.model.Commit
import com.mycompany.myapp.data.api.github.model.CommitDetails
import com.mycompany.myapp.withRecyclerView
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import org.hamcrest.Matchers.any
import org.hamcrest.Matchers.not
import org.junit.Before
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityEspressoTest {

    @JvmField
    @Rule
    var testRule = ActivityTestRule(MainActivity::class.java, false, false)

    @MockK
    lateinit var gitHubInteractor: GitHubInteractor

    @Ignore("MockK espresso incompatibility")
    @Test
    fun testBuildFingerprint() {
        coEvery {
            gitHubInteractor.loadCommits(any())
        } returns Result.success(mockk())

        testRule.launchActivity(null)
        onView(withId(R.id.fingerprint)).check(matches(withText(regex("Fingerprint: .+"))))
    }

    @Ignore("MockK espresso incompatibility")
    @Test
    fun testFetchCommitsEnabledState() {
        val response = LoadCommitsResponse(
                LoadCommitsRequest("username", "repository"),
                emptyList())
        coEvery {
            gitHubInteractor.loadCommits(any())
        } returns Result.success(response)

        testRule.launchActivity(null)
        onView(withId(R.id.fetch_commits)).check(matches(isEnabled()))

        onView(withId(R.id.username)).perform(clearText())
        onView(withId(R.id.fetch_commits)).check(matches(not(isEnabled())))

        onView(withId(R.id.username)).perform(typeText("username"))
        onView(withId(R.id.fetch_commits)).check(matches(isEnabled()))

        onView(withId(R.id.repository)).perform(clearText())
        onView(withId(R.id.fetch_commits)).check(matches(not(isEnabled())))
    }

    @Ignore("MockK espresso incompatibility")
    @Test
    fun testFetchAndDisplayCommits() {
        val response = buildMockLoadCommitsResponse()
        coEvery {
            gitHubInteractor.loadCommits(any())
        } returns Result.success(response)

        testRule.launchActivity(null)
        closeSoftKeyboard()

        onView(withRecyclerView(R.id.commits)
                .atPositionOnView(0, R.id.author))
                .check(matches(withText("Author: Test author")))
        onView(withRecyclerView(R.id.commits)
                .atPositionOnView(0, R.id.message))
                .check(matches(withText("Test commit message")))
    }

    private fun buildMockLoadCommitsResponse(): LoadCommitsResponse {
        val request = LoadCommitsRequest("madebyatomicrobot", "android-starter-project")
        val commit = Commit(CommitDetails("Test commit message", Author("Test author")))
        return LoadCommitsResponse(request, listOf(commit))
    }
}
