package com.atomicrobot.carbon.app

import android.app.Application
import androidx.annotation.VisibleForTesting
import com.atomicrobot.carbon.app.MainApplicationInitializer

import javax.inject.Inject

open class MainApplication : Application() {
    @Inject lateinit var initializer: MainApplicationInitializer

    override fun onCreate() {
        super.onCreate()


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
