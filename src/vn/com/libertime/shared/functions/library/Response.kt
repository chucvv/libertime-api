package vn.com.libertime.shared.functions.library

/**
 * Response model to expose in API response
 */
interface Response {
    val status: State
    val message: String
}

/**
 * HTTP Response Status. Used for evaluation of [HttpResponse] type.
 */
enum class State {
    SUCCESS, NOT_FOUND, FAILED, UNAUTHORIZED
}

fun generateResponse(state: State, message: String): Response {
    return object : Response {
        override val status: State = state
        override val message: String = message
    }
}