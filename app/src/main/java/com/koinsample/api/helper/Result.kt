package com.koinsample.api.helper

class Result<T> private constructor(
    var state: State = State.NONE,
    var data: T? = null,
    var errorMessage: String? = null
) {
    companion object {
        fun <T> loading(data: T? = null) =
            Result<T>(state = State.LOADING, data = data)

        fun <T> success(data: T?) =
            Result<T>(state = State.SUCCESS, data = data)

        fun <T> error(data: T? = null, errorMessage: String?) =
            Result(state = State.ERROR, data = data, errorMessage = errorMessage)
    }

    enum class State { NONE, LOADING, SUCCESS, ERROR }
}