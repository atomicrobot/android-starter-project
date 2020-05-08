package com.mycompany.myapp.app

import com.mycompany.myapp.data.OkHttpSecurityModifier

import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module


val VariantModule = module {
    single<Settings> {
        Settings(androidContext())
    }
    single {
        MainApplicationInitializer(application = androidApplication(), logger = get())
    }


    single<OkHttpSecurityModifier> {
        object : OkHttpSecurityModifier {
            override fun apply(builder: OkHttpClient.Builder) {


            }
        }

    }


}


