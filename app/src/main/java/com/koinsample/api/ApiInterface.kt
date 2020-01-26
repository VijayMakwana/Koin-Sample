package com.koinsample.api

import com.koinsample.api.model.RepoModel
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {
    @GET("repositories")
    fun getRepos(): Call<List<RepoModel>>
}