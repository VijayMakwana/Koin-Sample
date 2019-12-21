package com.gojek.assignment.api.helper

import kotlinx.coroutines.CompletableDeferred
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


suspend fun <T : Any> Call<T>.getResult(data: T? = null): Result<T> {
    try {
        val response = this.enqueueDeferredResponse().await()
        return if (response.isSuccessful) {
            Result.success(response.body())
        } else {
            //parse error from API
            val errorMessage = JSONObject(response.errorBody().toString()).getJSONObject(
                "error"
            ).getString("message")

            Result.error(data = data, errorMessage = errorMessage)

        }
    } catch (throwable: Throwable) {
        return Result.error(data = data, errorMessage = throwable.message)
    }
}


/**
 * Enqueues the current call, and return the response with the deferred type (Deferred<Response<T>>)
 * So you can get the response object with await() call on it.
 */
fun <T> Call<T>.enqueueDeferredResponse(callback: Callback<T>? = null): CompletableDeferred<Response<T>> {
    val deferred = CompletableDeferred<Response<T>>()
    deferred.invokeOnCompletion {
        if (deferred.isCancelled) {
            this.cancel()
        }
    }

    this.enqueue(object : Callback<T> {
        override fun onFailure(call: Call<T>, t: Throwable) {
            callback?.onFailure(call, t)
            deferred.completeExceptionally(t)
        }

        override fun onResponse(call: Call<T>, response: Response<T>) {
            callback?.onResponse(call, response)
            deferred.complete(response)
        }
    })
    return deferred
}
