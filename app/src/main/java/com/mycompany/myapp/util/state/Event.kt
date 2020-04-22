package com.mycompany.myapp.util.state

import androidx.lifecycle.MutableLiveData

open class Event<out T>(private val content: T) {

    var hasBeenHandled = false
        private set

    /**
     * Returns the content of the event and prevents it from being handled again
     */
    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    /**
     * Returns the content, even if it has been handled, and doesn't prevent it from being handled
     * again
     */
    fun peekContent(): T = content
}

fun <T> MutableLiveData<Event<T>>.postEvent(content: T) {
    this.postValue(Event(content))
}
