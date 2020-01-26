package com.koinsample.di

import android.content.Context
import com.koinsample.api.di.NetworkModule
import com.koinsample.api.di.NetworkModuleImpl
import com.koinsample.api.di.RepoRepository
import com.koinsample.api.di.RepoRepositoryImpl
import com.koinsample.ui.repositories.trending.TrendingRepoViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import org.mockito.Mockito.mock

val appModule = module {
    single<NetworkModule> { NetworkModuleImpl(mock(Context::class.java)) }

    single<RepoRepository> { RepoRepositoryImpl(get()) }

    viewModel { TrendingRepoViewModel(get()) }
}