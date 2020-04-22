package com.mycompany.myapp.app

import android.app.Application
import com.mycompany.myapp.data.DataModule
import com.mycompany.myapp.modules.CrashReporterModule
import com.mycompany.myapp.monitoring.LoggerModule
import com.mycompany.myapp.ui.ViewModelModule
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

open class MainApplication : Application() {

    val initializer: MainApplicationInitializer by inject()

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MainApplication)

            modules(LoggerModule, VariantModule, CrashReporterModule, DataModule, ViewModelModule)
        }

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
