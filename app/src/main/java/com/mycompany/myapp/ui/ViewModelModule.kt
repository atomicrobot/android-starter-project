package com.mycompany.myapp.ui

import com.mycompany.myapp.ui.main.MainViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val ViewModelModule = module {
    viewModel { MainViewModel(app = androidApplication(), gitHubInteractor = get(), loadingDelayMs = 25L) }
}