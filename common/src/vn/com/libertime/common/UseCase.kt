package vn.com.libertime.common

interface UseCase<P : Any, R : Any> {
    suspend fun invoke(params: P): Result<R>
}