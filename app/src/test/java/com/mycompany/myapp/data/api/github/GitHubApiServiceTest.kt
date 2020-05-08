package com.mycompany.myapp.data.api.github

import android.app.Instrumentation
import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.errorprone.annotations.Var
import com.mycompany.myapp.CoroutinesTestRule
import com.mycompany.myapp.app.VariantModule
import com.mycompany.myapp.data.DataModule
import com.mycompany.myapp.data.api.github.model.Commit
import com.mycompany.myapp.loadResourceAsString
import io.mockk.mockk
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.inject
import org.koin.test.mock.MockProviderRule
import org.koin.test.mock.declare
import org.koin.test.mock.declareMock
import retrofit2.Response
import java.net.UnknownHostException
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
class GitHubApiServiceTest : KoinTest {

    private lateinit var server: MockWebServer

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        androidContext(ApplicationProvider.getApplicationContext<Context>())
        modules(VariantModule, DataModule)
    }

    val api: GitHubApiService by inject()

    @Before
    fun setup() {
        server = MockWebServer()
        declare<String>(named("baseUrl")) { server.url("").toString() }
    }

    @After
    @Throws(Exception::class)
    fun tearDown() {
        server.shutdown()
    }

    @Test
    @Throws(Exception::class)
    fun testListCommitsSuccessful() = coroutinesTestRule.testDispatcher.runBlockingTest {
        server.enqueue(MockResponse().setBody("/api/listCommits_success.json".loadResourceAsString()))

        val response = api.listCommits("test_user", "test_repository")

        val serverRequest = server.takeRequest()
        assertEquals("GET", serverRequest.method)
        assertEquals("/repos/test_user/test_repository/commits", serverRequest.path)

        assertTrue(response.isSuccessful)
        val commits = response.body()
        assertEquals(1, commits!!.size.toLong())
        val commit = commits[0]
        assertEquals("test message", commit.commitMessage)
        assertEquals("test author", commit.author)
    }

    @Test
    @Throws(Exception::class)
    fun testListCommitsUnsuccessful() = coroutinesTestRule.testDispatcher.runBlockingTest {
        server.enqueue(MockResponse().setResponseCode(404).setBody("{\"message\": \"Not Found\"}"))
        server.start()

        val response = api.listCommits("test_user", "test_repository")


        assertFalse(response.isSuccessful)
        assertEquals(404, response.code().toLong())
    }

    @Test
    @Throws(Exception::class)
    fun testListCommitsNetworkError() = coroutinesTestRule.testDispatcher.runBlockingTest {
        val response = api.listCommits("test_user", "test_repository")
        assertTrue(response.errorBody() is UnknownHostException)
        // Note: You can't compare message text because that will be provided by the underlying runtime
    }
}