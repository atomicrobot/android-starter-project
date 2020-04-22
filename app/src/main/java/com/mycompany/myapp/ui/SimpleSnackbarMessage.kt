package com.mycompany.myapp.ui

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.mycompany.myapp.util.state.Event

/**
 * A LiveData used for Snackbar messages.
 *
 * Note that only one observer is going to be notified of changes.
 */
class SimpleSnackbarMessage : MutableLiveData<Event<String>>() {
    fun observe(owner: LifecycleOwner, observer: SnackbarObserver) {
        super.observe(owner, Observer { message ->
            message.getContentIfNotHandled()?.let {
                observer.onNewMessage(it)
            }
        })
    }

    interface SnackbarObserver {
        /**
         * Called when there is a new message to be shown.
         */
        fun onNewMessage(message: String)
    }
}