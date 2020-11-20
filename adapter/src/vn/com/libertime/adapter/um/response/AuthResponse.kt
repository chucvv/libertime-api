package vn.com.libertime.adapter.um.response

import vn.com.libertime.common.Response
import vn.com.libertime.common.State
import vn.com.libertime.port.um.entity.CredentialEntity

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