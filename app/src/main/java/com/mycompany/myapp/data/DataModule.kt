package com.mycompany.myapp.data


import com.mycompany.myapp.app.Settings
import com.mycompany.myapp.data.api.github.GitHubApiService
import com.mycompany.myapp.data.api.github.GitHubInteractor
import com.squareup.moshi.Moshi
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber
import java.io.File


interface OkHttpSecurityModifier {
    fun apply(builder: OkHttpClient.Builder)
}

val DataModule = module {

    single {
        val builder = OkHttpClient.Builder()
        val logging = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                Timber.tag("OkHttp").d(message)
            }
        })
        builder.addInterceptor(logging)
        get<OkHttpSecurityModifier>().apply(builder)
        builder.build()
    }

    single(named("baseUrl")) {
        get<Settings>().baseUrl
    }

    single<Converter.Factory> {
        val moshi = Moshi.Builder().build()
        MoshiConverterFactory.create(moshi)
    }

    single {
        Retrofit.Builder()
                .client(get())
                .baseUrl(get<String>(named("baseUrl")))
                .addConverterFactory(get())
                .build()
    }

    single { get<Retrofit>().create(GitHubApiService::class.java) }

    single { GitHubInteractor(androidContext(), get()) }

    single(named("diskCacheSize")) {
        // 50 MB
        (50 * 1024 * 1024).toLong()
    }
}
