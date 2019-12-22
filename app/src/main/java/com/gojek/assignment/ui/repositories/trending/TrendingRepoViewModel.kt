package com.gojek.assignment.ui.repositories.trending

import androidx.lifecycle.*
import com.gojek.assignment.api.di.RepoRepository
import com.gojek.assignment.api.helper.Result
import com.gojek.assignment.api.model.RepoModel
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class TrendingRepoViewModel(private val repoRepository: RepoRepository) : ViewModel() {

    private val mReposResult = MutableLiveData<Result<List<RepoModel>>>()
    val reposResult: LiveData<Result<List<RepoModel>>> = mReposResult

    val repoList = Transformations.map(reposResult) {
        it.data.orEmpty()
    }

    init {
        fetchTrendingRepos() // call initially
    }

    fun fetchTrendingRepos() = viewModelScope.launch {
        repoRepository.getTrendingRepos()
            .onStart {
                emit(Result.loading())
            }.asLiveData().observeForever { mReposResult.value = it }
    }

}