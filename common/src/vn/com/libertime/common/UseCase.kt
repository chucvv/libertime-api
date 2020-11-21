package vn.com.libertime.common

public interface UseCase<P : Any, R : Any> {
    public suspend fun invoke(params: P): Result<R>
}