package com.mycompany.myapp.modules

import com.mycompany.myapp.monitoring.CrashlyticsCrashReporter
import javax.inject.Singleton

val CrashReporterModule = module {
    single<provideCrashReporter> { CrashlyticsCrashReporter() }

}
