package com.gojek.assignment.ui.repositories.trending

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.gojek.assignment.api.di.RepoRepository
import com.gojek.assignment.api.helper.Result
import com.gojek.assignment.api.model.RepoModel
import kotlinx.coroutines.flow.onStart

class TrendingRepoViewModel(private val repoRepository: RepoRepository) : ViewModel() {

    val data: LiveData<Result<List<RepoModel>>> =
        liveData {
            emitSource(
                repoRepository.getTrendingRepos()
                    .onStart {
                        emit(Result.loading())
                    }.asLiveData()
            )
        }

}