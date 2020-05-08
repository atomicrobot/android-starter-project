package com.mycompany.myapp.modules

import com.mycompany.myapp.monitoring.CrashReporter
import com.mycompany.myapp.monitoring.LoggingOnlyCrashReporter
import org.koin.dsl.module

val CrashReporterModule = module {
    single<CrashReporter> { LoggingOnlyCrashReporter() }
}
