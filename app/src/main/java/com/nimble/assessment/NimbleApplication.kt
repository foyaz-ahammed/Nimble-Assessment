package com.nimble.assessment

import android.app.Application
import com.nimble.assessment.modules.repositoryModule
import com.nimble.assessment.modules.viewModelModule
import org.koin.core.context.startKoin

/**
 * Application class used on the project
 */
class NimbleApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            modules(repositoryModule, viewModelModule)
        }
    }
}