package com.gojek.assignment.api.di

import android.content.Context
import com.gojek.assignment.api.ApiInterface
import com.gojek.assignment.api.helper.Constants
import com.gojek.assignment.utils.isNetworkAvailable
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit


interface NetworkModule {
    fun getApiClient(): ApiInterface
}

class NetworkModuleImpl(private val context: Context) : NetworkModule {

    private fun createOkHttpClient(): OkHttpClient {
        // logging interceptor
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        // network interceptor
        val cacheControlInterceptor = Interceptor { chain ->
            val originalResponse: Response = chain.proceed(chain.request())

            // cache the rersponse for 2 hours duration
            val cacheHeaderValue =
                if (context.isNetworkAvailable()) "public, max-age=7200" else "public, only-if-cached, max-stale=0"

            return@Interceptor originalResponse.newBuilder()
                .removeHeader("Pragma")
                .removeHeader("Cache-Control")
                .header("Cache-Control", cacheHeaderValue)
                .build()
        }

        //setup cache
        val httpCacheDirectory = File(context.cacheDir, "responses")
        val cacheSize: Long = 10 * 1024 * 1024 // 10 MiB
        val cache = Cache(httpCacheDirectory, cacheSize)

        return OkHttpClient.Builder()
            .connectTimeout(60L, TimeUnit.SECONDS)
            .readTimeout(60L, TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor)
            .addNetworkInterceptor(cacheControlInterceptor)
            .cache(cache)
            .build()
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