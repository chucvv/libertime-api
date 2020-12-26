package vn.com.libertime.adapter.client_side.um.response

import vn.com.libertime.common.Response
import vn.com.libertime.common.State
import vn.com.libertime.port.um.entity.SearchUserProfile

public data class UserSearchInRadiusResponse(
    override val status: State,
    override val message: String? = null,
    val data: List<SearchUserProfile>? = null,
) : Response {
    public companion object {
        public fun failed(message: String?): UserSearchInRadiusResponse = UserSearchInRadiusResponse(
            status = State.FAILED,
            message = message
        )

        public fun notFound(message: String?): UserSearchInRadiusResponse = UserSearchInRadiusResponse(
            status = State.NOT_FOUND,
            message = message
        )

        public fun success(data: List<SearchUserProfile>?): UserSearchInRadiusResponse = UserSearchInRadiusResponse(
            status = State.SUCCESS,
            data = data,
        )
    }
}