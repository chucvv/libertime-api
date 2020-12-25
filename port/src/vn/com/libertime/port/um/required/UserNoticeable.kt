package vn.com.libertime.port.um.required

import vn.com.libertime.port.um.entity.UserEvent

public interface UserNoticeable {
    public suspend fun notify(userEvent: UserEvent)
}