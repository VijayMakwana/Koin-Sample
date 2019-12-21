package com.gojek.assignment.api.di

import com.gojek.assignment.api.helper.Result
import com.gojek.assignment.api.helper.getResult
import com.gojek.assignment.api.model.RepoModel
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