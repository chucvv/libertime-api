package vn.com.libertime.port.um.required

import vn.com.libertime.port.um.entity.UserEvent

public interface UserNotificationPort {
    public suspend fun notify(userEvent: UserEvent)
}