package com.mycompany.myapp.ui

import android.app.Application
import android.os.Bundle
import android.os.Parcelable
import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import androidx.lifecycle.AndroidViewModel

abstract class BaseViewModel<State : Parcelable>(
        app: Application,
        private val stateKey: String,
        protected var state: State) : AndroidViewModel(app), Observable {

    private var viewModelInitialized: Boolean = false

    fun saveState(bundle: Bundle) {
        bundle.putParcelable(stateKey, state)
    }

    fun restoreState(bundle: Bundle?) {
        if (bundle != null && bundle.containsKey(stateKey)) {
            state = bundle.getParcelable(stateKey)!!
        }
    }

    fun onResume() {
        if (!viewModelInitialized) {
            viewModelInitialized = true
            setupViewModel()
        }
    }

    abstract fun setupViewModel()

    /* Start of databinding behavior pillaged from BaseObservable */

    @Transient private var databindingCallbacks: PropertyChangeRegistry? = null

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback) {
        synchronized(this) {
            if (databindingCallbacks == null) {
                databindingCallbacks = PropertyChangeRegistry()
            }
        }

        databindingCallbacks!!.add(callback)
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback) {
        synchronized(this) {
            if (databindingCallbacks == null) {
                return
            }
        }

        databindingCallbacks!!.remove(callback)
    }

    fun notifyChange() {
        synchronized(this) {
            if (databindingCallbacks == null) {
                return
            }
        }

        databindingCallbacks!!.notifyCallbacks(this, 0, null)
    }

    fun notifyPropertyChanged(fieldId: Int) {
        synchronized(this) {
            if (databindingCallbacks == null) {
                return
            }
        }

        databindingCallbacks!!.notifyCallbacks(this, fieldId, null)
    }

    /* End of databinding behavior pillaged from BaseObservable */
}