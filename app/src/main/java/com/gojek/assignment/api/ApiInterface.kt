package com.gojek.assignment.api

import com.gojek.assignment.api.model.RepoModel
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {
    @GET("repositories")
    fun getRepos(): Call<List<RepoModel>>
}