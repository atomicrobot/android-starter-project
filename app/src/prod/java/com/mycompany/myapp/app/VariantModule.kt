package com.mycompany.myapp.app

import com.mycompany.myapp.data.OkHttpSecurityModifier
import okhttp3.OkHttpClient

@Module
class VariantModule {

    @Provides
    fun provideOkHttpClientTrustAllBinding(settings: Settings): OkHttpSecurityModifier {
        return object: OkHttpSecurityModifier {
            override fun apply(builder: OkHttpClient.Builder) {
                /* No op */
            }
        }
    }
}
