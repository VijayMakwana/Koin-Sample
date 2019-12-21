package com.gojek.assignment.module

import com.gojek.assignment.api.di.NetworkModule
import com.gojek.assignment.api.di.NetworkModuleImpl
import com.gojek.assignment.api.di.RepoRepository
import com.gojek.assignment.api.di.RepoRepositoryImpl
import com.gojek.assignment.ui.repositories.trending.TrendingRepoViewModel
import org.junit.Test
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.test.check.checkModules

class ModuleUnitTest {
    @Test
    fun checkAllModulesFromApp() {
        val appModule = module {
            single<NetworkModule> { NetworkModuleImpl() }

            single<RepoRepository> { RepoRepositoryImpl(get()) }

            viewModel { TrendingRepoViewModel(get()) }
        }
        startKoin {
            modules(appModule)
        }.checkModules()
    }
}