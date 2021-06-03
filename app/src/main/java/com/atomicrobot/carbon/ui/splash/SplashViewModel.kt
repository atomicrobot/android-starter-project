package com.atomicrobot.carbon.ui.splash

import android.app.Application
import android.os.Parcelable
import com.atomicrobot.carbon.ui.BaseViewModel
import com.atomicrobot.carbon.ui.NavigationEvent
import kotlinx.android.parcel.Parcelize
import javax.inject.Inject

class SplashViewModel(
        private val app: Application)
    : BaseViewModel<SplashViewModel.State>(app, STATE_KEY, State()) {

    @Parcelize
    class State : Parcelable

    sealed class ViewNavigation {
        object FirstTime : ViewNavigation()
    }

    val navigationEvent = NavigationEvent<ViewNavigation>()

    override fun setupViewModel() {
        navigationEvent.postValue(ViewNavigation.FirstTime)
    }

    companion object {
        private const val STATE_KEY = "SplashViewModelState"  // NON-NLS
    }
}
