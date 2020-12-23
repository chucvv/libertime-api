package vn.com.libertime.adapter.client_side.um.kafka

import com.sksamuel.avro4k.AvroName
import kotlinx.serialization.Serializable

@Serializable
@AvroName("UserDto")
internal data class UserConsumeMessageDto(
    @AvroName("name")
    val name: String,
    @Serializable
    @AvroName("email")
    val email: String,
)