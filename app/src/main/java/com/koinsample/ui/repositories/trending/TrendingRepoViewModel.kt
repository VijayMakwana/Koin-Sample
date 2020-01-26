package com.koinsample.ui.repositories.trending

import androidx.lifecycle.*
import com.koinsample.api.di.RepoRepository
import com.koinsample.api.helper.Result
import com.koinsample.api.model.RepoModel
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
                emit(Result.loading(if (repoList.value.isNullOrEmpty()) null else repoList.value))
            }.asLiveData().observeForever { mReposResult.value = it }
    }

    // sort by star
    fun sortByStar() {
        mReposResult.value = Result.success(repoList.value?.sortedBy { it.stars })
    }

    // sort by name
    fun sortByName() {
        mReposResult.value = Result.success(repoList.value?.sortedBy { it.name })
    }

}