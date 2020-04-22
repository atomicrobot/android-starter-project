package com.mycompany.myapp.data


import com.mycompany.myapp.app.Settings
import com.mycompany.myapp.data.api.github.GitHubApiService
import com.mycompany.myapp.data.api.github.GitHubInteractor
import com.squareup.moshi.Moshi

import okhttp3.Cache
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.File

interface OkHttpSecurityModifier {
    fun apply(builder: OkHttpClient.Builder)
}

val DataModule = module {

    single {
        val cacheDir = File(androidApplication().cacheDir, "http")
        Cache(cacheDir, get<Long>(named("diskCacheSize")).toLong())
    }

    single {
        val builder = OkHttpClient.Builder()
        builder.cache(get())
        get<OkHttpSecurityModifier>().apply(builder)
        builder.build()
    }

    single(named("baseUrl")) {
        get<Settings>().baseUrl
    }

    single {
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
