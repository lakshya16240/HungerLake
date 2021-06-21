package com.example.hungerlake

import android.app.Application
import com.example.hungerlake.utils.restAppModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.fragment.koin.fragmentFactory
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class HungerLakeApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@HungerLakeApp)
            androidLogger(Level.DEBUG)
            fragmentFactory()
            modules(listOf(restAppModule))
        }
    }
}