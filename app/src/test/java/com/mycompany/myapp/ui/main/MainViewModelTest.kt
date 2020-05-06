package com.mycompany.myapp.ui.main

import android.app.Application
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mycompany.myapp.CoroutinesTestRule
import com.mycompany.myapp.data.api.github.GitHubInteractor
import com.mycompany.myapp.data.api.github.GitHubInteractor.LoadCommitsResponse
import com.mycompany.myapp.data.api.github.model.Commit
import com.mycompany.myapp.util.state.AsyncState
import io.mockk.impl.annotations.MockK
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*


@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class MainViewModelTest {

    @MockK
    private lateinit var githubInteractor: GitHubInteractor

    private lateinit var viewModel: MainViewModel

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Before
    fun setup() {
        MockKAnnotations.init(this)

        val app = ApplicationProvider.getApplicationContext<Application>()
        viewModel = MainViewModel(
                app,
                githubInteractor,
                0)
    }

    @Test
    fun testGetVersion() {
        // CI systems can change the build number so we are a little more flexible on what to expect
        val expectedPattern = "1.0 b[1-9][0-9]*".toRegex()
        assertTrue("1.0 b123".matches(expectedPattern))
        assertTrue(viewModel.getVersion().matches(expectedPattern))
    }

    @Test
    fun testGetFingerprint() {
        // CI systems can change the build number so we are a little more flexible on what to expect
        val expectedPattern = "[a-zA-Z0-9]+".toRegex()
        assertTrue("0569b5cd8".matches(expectedPattern))
        assertTrue(viewModel.getFingerprint().matches(expectedPattern))
    }

    @Test
    fun testFetchCommitsEnabled() {
        viewModel.username = "test"
        viewModel.repository = ""
        assertFalse(viewModel.isFetchCommitsEnabled())

        viewModel.username = ""
        viewModel.repository = "test"
        assertFalse(viewModel.isFetchCommitsEnabled())

        viewModel.username = ""
        viewModel.repository = ""
        assertFalse(viewModel.isFetchCommitsEnabled())

        viewModel.username = "test"
        viewModel.repository = ""
        assertFalse(viewModel.isFetchCommitsEnabled())

        viewModel.username = ""
        viewModel.repository = "test"
        assertFalse(viewModel.isFetchCommitsEnabled())

        viewModel.username = "test"
        viewModel.repository = "test"
        assertTrue(viewModel.isFetchCommitsEnabled())
    }

    @Test
    fun testFetchCommits() = coroutinesTestRule.testDispatcher.runBlockingTest {
        val mockResult = mockk<LoadCommitsResponse>()
        val mockCommit = mockk<Commit>()

        every { mockResult.commits } returns listOf(mockCommit)
        coEvery { githubInteractor.loadCommits(any()) } returns(Result.success(mockResult))

        assertEquals(AsyncState.Idle, viewModel.commits)
        viewModel.fetchCommits()
         assertTrue(viewModel.commits  is AsyncState.Success)
        (viewModel.commits as AsyncState.Success).let {
            assertEquals(listOf(mockCommit), it.value)
        }
    }
}