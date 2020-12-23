package vn.com.libertime.adapter.client_side.um.kafka

import com.sksamuel.avro4k.AvroName
import com.sksamuel.avro4k.AvroNamespace
import kotlinx.serialization.Serializable
import vn.com.libertime.port.um.entity.UserEvent

@Serializable
@AvroName("UserDto")
@AvroNamespace("dto")
internal data class UserMessageDTO(
    val id: String,
    @AvroName("name")
    val name: String,
    @Serializable
    @AvroName("email")
    val email: String?
)

internal fun UserEvent.toMessageDTO(): UserMessageDTO =
    UserMessageDTO(
        id = user.userId,
        name = user.username,
        email = user.email
    )