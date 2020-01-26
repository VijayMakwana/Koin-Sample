package com.koinsample

import android.app.Application
import com.koinsample.api.di.NetworkModule
import com.koinsample.api.di.NetworkModuleImpl
import com.koinsample.api.di.RepoRepository
import com.koinsample.api.di.RepoRepositoryImpl
import com.koinsample.ui.repositories.trending.TrendingRepoViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        // all the app functionality related modules here
        val appModule = module {
            single<NetworkModule> { NetworkModuleImpl(androidContext()) }

            single<RepoRepository> { RepoRepositoryImpl(get()) }

            viewModel { TrendingRepoViewModel(get()) }
        }

        // start dependency injection with koin
        startKoin {
            androidLogger() // log the info level
            androidContext(this@App) // provide application context
            modules(appModule) // app modules
        }
    }

}