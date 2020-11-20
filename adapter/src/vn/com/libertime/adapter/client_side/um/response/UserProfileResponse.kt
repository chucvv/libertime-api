package vn.com.libertime.adapter.client_side.um.response

import vn.com.libertime.common.Response
import vn.com.libertime.common.State

data class UserProfileResponse(
    override val status: State,
    override val message: String? = null,
    val data: MeResponse? = null,
) : Response {
    companion object {
        fun failed(message: String?) = UserProfileResponse(
            status = State.FAILED,
            message = message
        )

        fun notFound(message: String?) = UserProfileResponse(
            status = State.NOT_FOUND,
            message = message
        )

        fun success(meResponse: MeResponse?) = UserProfileResponse(
            status = State.SUCCESS,
            data = meResponse,
        )
    }
}