package com.mycompany.myapp.ui.main

import android.app.Application
import android.os.Parcelable
import androidx.annotation.VisibleForTesting
import androidx.databinding.Bindable
import androidx.lifecycle.viewModelScope
import com.mycompany.myapp.BR
import com.mycompany.myapp.BuildConfig
import com.mycompany.myapp.R
import com.mycompany.myapp.data.api.github.GitHubInteractor
import com.mycompany.myapp.data.api.github.GitHubInteractor.LoadCommitsRequest
import com.mycompany.myapp.data.api.github.model.Commit
import com.mycompany.myapp.ui.BaseViewModel
import com.mycompany.myapp.ui.SimpleSnackbarMessage
import com.mycompany.myapp.util.state.AsyncState
import com.mycompany.myapp.util.state.Event
import kotlinx.android.parcel.Parcelize
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel(
        private val app: Application,
        private val gitHubInteractor: GitHubInteractor,
        private val loadingDelayMs: Long
) : BaseViewModel<MainViewModel.State>(app, STATE_KEY, State()) {

    @Parcelize
    class State(
            var username: String = "",
            var repository: String = "") : Parcelable

    override fun setupViewModel() {
        username = "madebyatomicrobot"  // NON-NLS
        repository = "android-starter-project"  // NON-NLS

        fetchCommits()
    }

    @VisibleForTesting
    internal var commits: AsyncState<List<Commit>> = AsyncState.Idle
        set(value) {
            field = value

            notifyPropertyChanged(BR.loading)
            notifyPropertyChanged(BR.commits)
            notifyPropertyChanged(BR.fetchCommitsEnabled)

            when (value) {
                is AsyncState.Fail -> snackbarMessage.value = Event(value.throwable.message ?: "Some Default Message")
            }
        }

    val snackbarMessage = SimpleSnackbarMessage()

    var username: String
        @Bindable get() = state.username
        set(value) {
            state.username = value
            notifyPropertyChanged(BR.username)
        }

    var repository: String
        @Bindable get() = state.repository
        set(value) {
            state.repository = value
            notifyPropertyChanged(BR.repository)
        }

    @Bindable("username", "repository")
    fun isFetchCommitsEnabled(): Boolean = commits !is AsyncState.Loading && !username.isEmpty() && !repository.isEmpty()

    @Bindable
    fun isLoading(): Boolean = commits is AsyncState.Loading

    @Bindable
    fun getCommits() = commits.let {
        when (it) {
            is AsyncState.Success -> it.value
            else -> emptyList()
        }
    }

    fun getVersion(): String = BuildConfig.VERSION_NAME

    fun getFingerprint(): String = BuildConfig.VERSION_FINGERPRINT

    fun fetchCommits() {
        commits = AsyncState.Loading
        viewModelScope.launch {
            delay(loadingDelayMs)
            val result = gitHubInteractor.loadCommits(LoadCommitsRequest(username, repository))
            result.fold({
                commits = AsyncState.Success(it.commits)
            }, {
                commits = AsyncState.Fail(it)
            })
        }
    }

    companion object {
        private const val STATE_KEY = "MainViewModelState"  // NON-NLS
    }
}
