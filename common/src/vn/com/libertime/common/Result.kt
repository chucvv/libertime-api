package vn.com.libertime.common

public sealed class Result<out T : Any> {
    public data class Success<out T : Any>(val data: T) : Result<T>()
    public sealed class Error(public open val exception: String?) : Result<Nothing>() {
        public data class InternalSystemException(override val exception: String? = "") : Error(exception)
        public data class BusinessException(override val exception: String? = "") : Error(exception)
    }
}

public fun <T : Any> Result<T>.takeSuccess(): T? = (this as? Result.Success)?.data

public fun <T : Any> Result<T>.takeException(): String? = (this as? Result.Error)?.exception