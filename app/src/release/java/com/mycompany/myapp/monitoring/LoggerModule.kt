package com.mycompany.myapp.monitoring

import org.koin.dsl.module
import timber.log.Timber
import timber.log.Timber.DebugTree

val LoggerModule = module {
    single<Timber.Tree> { DebugTree() }
}