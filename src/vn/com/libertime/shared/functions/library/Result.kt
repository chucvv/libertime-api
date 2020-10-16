package vn.com.libertime.shared.functions.library

sealed class Result<out T : Any> {
    data class Success<out T : Any>(val data: T) : Result<T>()
    sealed class Error(exception: Exception) : Result<Nothing>() {
        data class StorageException(val exception: Exception) : Error(exception)
        data class BusinessException(val exception: Exception) : Error(exception)
    }
}

fun <T : Any> Result<T>.takeSuccess(): T? = (this as? Result.Success)?.data