package vn.com.libertime.shared.functions.library

sealed class Result<out T : Any> {
    data class Success<out T : Any>(val data: T) : Result<T>()
    sealed class Error(open val exception: Exception) : Result<Nothing>() {
        data class StorageException(override val exception: Exception) : Error(exception)
        data class BusinessException(override val exception: Exception) : Error(exception)
    }
}

fun <T : Any> Result<T>.takeSuccess(): T? = (this as? Result.Success)?.data

fun <T : Any> Result<T>.takeException(): String? = (this as? Result.Error)?.exception?.cause?.message