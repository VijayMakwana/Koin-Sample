package com.gojek.assignment.api.di

import com.gojek.assignment.api.ApiInterface
import com.gojek.assignment.api.helper.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


interface NetworkModule {
    fun getApiClient(): ApiInterface
}

class NetworkModuleImpl : NetworkModule {

    private fun createOkHttpClient(): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .connectTimeout(60L, TimeUnit.SECONDS)
            .readTimeout(60L, TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor).build()
    }

    private fun createWebService(okHttpClient: OkHttpClient): ApiInterface {
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.SERVER_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create()).build()
        return retrofit.create(ApiInterface::class.java)
    }

    override fun getApiClient(): ApiInterface =
        createWebService(createOkHttpClient())

}