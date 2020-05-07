package com.mycompany.myapp.data.api.github

import android.app.Application
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mycompany.myapp.CoroutinesTestRule
import com.mycompany.myapp.data.api.github.GitHubInteractor.LoadCommitsRequest
import com.mycompany.myapp.data.api.github.model.CommitTestHelper.stubCommit
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.Response
import java.util.Arrays.asList

@RunWith(AndroidJUnit4::class)
class GitHubInteractorTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @RelaxedMockK
    lateinit var api: GitHubApiService

    private lateinit var interactor: GitHubInteractor

    @Before
    fun setup() {
        MockKAnnotations.init(this)

        val context = ApplicationProvider.getApplicationContext<Application>()
        interactor = GitHubInteractor(context, api)
    }

    @Test
    @Throws(Exception::class)
    fun testLoadCommits() = coroutinesTestRule.testDispatcher.runBlockingTest {
        val mockResponse = Response.success(asList(stubCommit("test name", "test message")))
        coEvery { api.listCommits(any(), any()) } returns mockResponse

        val response = interactor.loadCommits(LoadCommitsRequest("user", "repo"))

        assertEquals("user", response.getOrThrow().request.user)
        assertEquals("repo", response.getOrThrow().request.repository)
        assertEquals(1, response.getOrThrow().commits.size.toLong())

        val commit = response.getOrThrow().commits[0]
        assertEquals("test name", commit.author)
        assertEquals("test message", commit.commitMessage)
    }
}