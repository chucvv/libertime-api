package vn.com.libertime.shared.functions.library

import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent

@KoinApiExtension
interface UseCase<P : Any, R : Any> : KoinComponent {
    fun invoke(params: P): Result<R>
}