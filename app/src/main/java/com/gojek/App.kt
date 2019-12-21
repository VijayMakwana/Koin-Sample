package com.gojek

import android.app.Application
import com.gojek.assignment.api.di.NetworkModule
import com.gojek.assignment.api.di.NetworkModuleImpl
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.dsl.module

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        // all the app functionality related modules here
        val appModule = module {
            single<NetworkModule> { NetworkModuleImpl() }
        }

        // start dependency injection with koin
        startKoin {
            androidLogger() // log the info level
            androidContext(this@App) // provide application context
            modules(appModule) // app modules
        }
    }

}