package vn.com.libertime.adapter.client_side.um.response

import vn.com.libertime.common.Response
import vn.com.libertime.common.State

public data class UserProfileResponse(
    override val status: State,
    override val message: String? = null,
    val data: MeResponse? = null,
) : Response {
    public companion object {
        public fun failed(message: String?): UserProfileResponse = UserProfileResponse(
            status = State.FAILED,
            message = message
        )

        public fun notFound(message: String?): UserProfileResponse = UserProfileResponse(
            status = State.NOT_FOUND,
            message = message
        )

        public fun success(meResponse: MeResponse?): UserProfileResponse = UserProfileResponse(
            status = State.SUCCESS,
            data = meResponse,
        )
    }
}