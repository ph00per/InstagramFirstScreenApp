package com.ph00.instagramfirstpageapp

import android.app.Application
import com.ph00.instagramfirstpageapp.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(
                listOf(
                    viewModelModule,
                    repositoryModule,
                    apiModule,
                    netModule
                )
            )
        }
    }
}