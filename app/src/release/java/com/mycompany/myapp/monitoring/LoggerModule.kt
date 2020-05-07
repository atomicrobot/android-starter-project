package com.mycompany.myapp.monitoring

import com.mycompany.myapp.monitoring.model.NoOpTree
import dagger.Module
import dagger.Provides
import timber.log.Timber
import javax.inject.Singleton

val LoggerModule = module {
    single<Timber.Tree> { NoOpTree() }
}
