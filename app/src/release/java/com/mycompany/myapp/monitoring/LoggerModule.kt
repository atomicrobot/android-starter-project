package com.mycompany.myapp.monitoring

import com.mycompany.myapp.monitoring.model.NoOpTree
import timber.log.Timber
import javax.inject.Singleton

@Module
class LoggerModule {
    @Singleton
    @Provides
    fun provideTimberTree() : Timber.Tree = NoOpTree()
}
