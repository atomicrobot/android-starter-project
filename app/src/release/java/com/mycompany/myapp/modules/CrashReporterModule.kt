package com.mycompany.myapp.modules

import com.mycompany.myapp.monitoring.CrashlyticsCrashReporter
import javax.inject.Singleton

@Module
class CrashReporterModule {
    @Singleton
    @Provides
    fun provideCrashReporter() = CrashlyticsCrashReporter()
}
