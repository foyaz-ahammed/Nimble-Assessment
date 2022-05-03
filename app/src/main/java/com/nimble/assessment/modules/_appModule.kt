package com.nimble.assessment.modules

import com.nimble.assessment.repository.NimbleRepository
import com.nimble.assessment.viewmodels.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val repositoryModule = module {
    single { NimbleRepository(androidContext()) }
}

val viewModelModule = module {
    viewModel { MainViewModel(get()) }
}