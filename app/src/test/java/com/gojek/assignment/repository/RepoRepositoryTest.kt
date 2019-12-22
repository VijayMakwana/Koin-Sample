package com.gojek.assignment.repository

import com.gojek.assignment.api.di.RepoRepository
import com.gojek.assignment.di.appModule
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject

class RepoRepositoryTest : KoinTest {
    private val repository by inject<RepoRepository>()

    @Before
    fun before() {
        startKoin { modules(appModule) }
    }

    @After
    fun after() {
        stopKoin()
    }

    @Test
    fun testFetchRepos() {
        val repos = runBlocking { repository.getTrendingRepos() }
        Assert.assertNotNull(repos)
    }

}