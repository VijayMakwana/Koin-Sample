package com.koinsample.api.di

import com.koinsample.api.helper.Result
import com.koinsample.api.helper.getResult
import com.koinsample.api.model.RepoModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface RepoRepository {
    suspend fun getTrendingRepos(): Flow<Result<List<RepoModel>>>
}

class RepoRepositoryImpl(private val networkModule: NetworkModule) : RepoRepository {
    override suspend fun getTrendingRepos(): Flow<Result<List<RepoModel>>> = flow {
        emit(networkModule.getApiClient().getRepos().getResult())
    }
}