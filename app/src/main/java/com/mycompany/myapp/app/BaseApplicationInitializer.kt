package com.mycompany.myapp.app

import android.app.Application
import android.content.Intent

import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.security.ProviderInstaller
import com.google.android.gms.security.ProviderInstaller.ProviderInstallListener
import com.squareup.leakcanary.LeakCanary

import timber.log.Timber
import timber.log.Timber.Tree

abstract class BaseApplicationInitializer(
        protected val application: Application,
        private val logger: Tree
) {

    open fun initialize() {
        LeakCanary.install(application)

        Timber.plant(logger)

        upgradeSecurityProvider()
    }

    private fun upgradeSecurityProvider() {
        ProviderInstaller.installIfNeededAsync(application, object : ProviderInstallListener {
            override fun onProviderInstalled() {

            }

            override fun onProviderInstallFailed(errorCode: Int, recoveryIntent: Intent) {
                GoogleApiAvailability.getInstance().showErrorNotification(application, errorCode)
            }
        })
    }
}
