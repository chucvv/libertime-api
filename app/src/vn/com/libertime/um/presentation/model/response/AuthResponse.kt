package vn.com.libertime.um.presentation.model.response

import vn.com.libertime.shared.functions.library.Response
import vn.com.libertime.shared.functions.library.State
import vn.com.libertime.um.domain.entity.CredentialEntity

/**
 * Response model used in Authentication API. For e.g. Login/Register.
 */
data class AuthResponse(
    override val status: State,
    override val message: String? = null,
    val credential: CredentialEntity? = null
) : Response {

    companion object {

        fun failed(message: String?) = AuthResponse(
            status = State.FAILED,
            message = message
        )

        fun unauthorized(message: String?) = AuthResponse(
            status = State.UNAUTHORIZED,
            message = message
        )

        fun success(token: CredentialEntity) = AuthResponse(
            status = State.SUCCESS,
            credential = token
        )
    }
}