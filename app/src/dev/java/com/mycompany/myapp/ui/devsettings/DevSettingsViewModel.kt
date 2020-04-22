package com.mycompany.myapp.ui.devsettings

import android.app.Application
import android.os.Parcelable
import androidx.databinding.Bindable
import com.mycompany.myapp.BR
import com.mycompany.myapp.app.Settings
import com.mycompany.myapp.ui.BaseViewModel
import kotlinx.android.parcel.Parcelize

class DevSettingsViewModel (
        private val app: Application,
        private val settings: Settings)
    : BaseViewModel<DevSettingsViewModel.State>(app, STATE_KEY, State()) {

    @Parcelize
    data class State(
            var baseUrl: String = "",
            var trustAllSSL: Boolean = false) : Parcelable

    override fun setupViewModel() {
        baseUrl = settings.baseUrl
        trustAllSSL = settings.trustAllSSL
    }

    var baseUrl: String
        @Bindable get() = state.baseUrl
        set(value) {
            state.baseUrl = value
            notifyPropertyChanged(BR.baseUrl)
        }

    var trustAllSSL: Boolean
        @Bindable get() = state.trustAllSSL
        set(value) {
            state.trustAllSSL = value
            notifyPropertyChanged(BR.trustAllSSL)
        }

    fun saveSettings() {
        settings.baseUrl = state.baseUrl
        settings.trustAllSSL = state.trustAllSSL
    }

    companion object {
        private const val STATE_KEY = "DevSettingsViewModelState"  // NON-NLS
    }
}
