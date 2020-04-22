package com.mycompany.myapp.app

import android.app.Application
import androidx.annotation.VisibleForTesting

import javax.inject.Inject

open class MainApplication : Application() {
    @set:VisibleForTesting

    @Inject lateinit var initializer: MainApplicationInitializer

    override fun onCreate() {
        super.onCreate()

        component.inject(this)

        initializeApplication()
    }

    /**
     * For Espresso tests we *do not* want to setupViewModel the full application and instead will
     * favor mocking out non-ui dependencies. This should be overridden to a no-op if initialization
     * should not occur.
     */
    protected open fun initializeApplication() {
        initializer.initialize()
    }

    open fun isTesting() = false
}
