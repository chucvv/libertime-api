package vn.com.libertime.adapter.client_side.um.response

import vn.com.libertime.common.Response
import vn.com.libertime.common.State
import vn.com.libertime.port.um.entity.CredentialEntity

/**
 * Response model used in Authentication API. For e.g. Login/Register.
 */
public data class AuthResponse(
    override val status: State,
    override val message: String? = null,
    val credential: CredentialEntity? = null
) : Response {

    public companion object {

        public fun failed(message: String?): AuthResponse = AuthResponse(
            status = State.FAILED,
            message = message
        )

        public fun unauthorized(message: String?): AuthResponse = AuthResponse(
            status = State.UNAUTHORIZED,
            message = message
        )

        public fun success(token: CredentialEntity): AuthResponse = AuthResponse(
            status = State.SUCCESS,
            credential = token
        )
    }
}