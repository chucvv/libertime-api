package vn.com.libertime.um.presentation.model.response

import vn.com.libertime.shared.functions.library.Response
import vn.com.libertime.shared.functions.library.State

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

        fun success(meResponse: MeResponse) = UserProfileResponse(
            status = State.SUCCESS,
            data = meResponse,
        )
    }
}