package com.claudiuluca.ingtest.data

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
 */
sealed class Result<out T> {

    data class Success<out T>(val data: T) : Result<T>()
    data class Error(
        val code: Int = ErrorCode.DEFAULT.code
    ) : Result<Nothing>()

    object Loading : Result<Nothing>()

    object None : Result<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$code]"
            Loading -> "Loading"
            None -> "None"
        }
    }
}

val Result<*>.succeeded
    get() = this is Result.Success && data != null

enum class ErrorCode(val code: Int) {
    DEFAULT(-1),
    NO_DATA_CONNECTION(0)
}
