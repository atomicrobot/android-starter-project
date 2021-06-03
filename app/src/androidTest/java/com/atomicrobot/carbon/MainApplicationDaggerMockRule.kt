package com.atomicrobot.carbon

import com.atomicrobot.carbon.TestUtils.getAppUnderTest
import com.atomicrobot.carbon.app.AndroidModule
import com.atomicrobot.carbon.app.AppModule
import com.atomicrobot.carbon.app.VariantModule
import com.atomicrobot.carbon.data.DataModule
import com.atomicrobot.carbon.modules.CrashReporterModule
import com.atomicrobot.carbon.monitoring.LoggerModule
import it.cosenonjaviste.daggermock.DaggerMockRule

class MainApplicationDaggerMockRule : DaggerMockRule<ApplicationComponent>(
        ApplicationComponent::class.java,
        VariantModule(),
        AndroidModule(getAppUnderTest()),
        AppModule(0),
        LoggerModule(),
        CrashReporterModule(),
        DataModule()) {
    init {
        set { component -> getAppUnderTest().component = component }
    }
}
