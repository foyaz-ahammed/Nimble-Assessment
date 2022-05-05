package com.nimble.assessment

import android.app.Application
import com.nimble.assessment.modules.databaseModule
import com.nimble.assessment.modules.networkModule
import com.nimble.assessment.modules.repositoryModule
import com.nimble.assessment.modules.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

/**
 * Application class used on the project
 */
class NimbleApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@NimbleApplication)
            modules(repositoryModule, networkModule, viewModelModule, databaseModule)
        }
    }
}