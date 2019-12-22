package com.gojek.assignment.di

import android.content.Context
import com.gojek.assignment.api.di.NetworkModule
import com.gojek.assignment.api.di.NetworkModuleImpl
import com.gojek.assignment.api.di.RepoRepository
import com.gojek.assignment.api.di.RepoRepositoryImpl
import com.gojek.assignment.ui.repositories.trending.TrendingRepoViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import org.mockito.Mockito.mock

val appModule = module {
    single<NetworkModule> { NetworkModuleImpl(mock(Context::class.java)) }

    single<RepoRepository> { RepoRepositoryImpl(get()) }

    viewModel { TrendingRepoViewModel(get()) }
}