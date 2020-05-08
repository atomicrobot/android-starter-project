package com.mycompany.myapp.data.api.github

import android.content.Context
import android.util.Log
import com.mycompany.myapp.R
import com.mycompany.myapp.data.api.github.model.Commit

import retrofit2.Response
import timber.log.Timber

class GitHubInteractor(private val context: Context, private val api: GitHubApiService) {

    class LoadCommitsRequest(val user: String, val repository: String)
    class LoadCommitsResponse(val request: LoadCommitsRequest, val commits: List<Commit>)

    suspend fun loadCommits(request: LoadCommitsRequest): Result<LoadCommitsResponse> {
        return try {
            val response = api.listCommits(request.user, request.repository)
            checkResponse(response, context.getString(R.string.error_get_commits_error))
            val commits = response.body() ?: emptyList()
            return Result.success(LoadCommitsResponse(request, commits))
        } catch (throwable: Throwable) {
            Timber.e(throwable)
            Result.failure(throwable)
        }
    }

    private fun <T> checkResponse(response: Response<T>, message: String): Response<T> {
        return when {
            response.isSuccessful -> response
            else -> throw IllegalStateException(message)
        }
    }
}
