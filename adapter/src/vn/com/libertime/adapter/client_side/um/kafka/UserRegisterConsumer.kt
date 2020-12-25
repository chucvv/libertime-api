package vn.com.libertime.adapter.client_side.um.kafka

import com.sksamuel.avro4k.Avro
import vn.com.libertime.port.um.required.Logger
import vn.com.libertime.kafka.consumer.clientConsumer

internal suspend fun consumeRegisterUser(
    bootstrapServers: String,
    schemaUrl: String,
    log: Logger
) {
    clientConsumer(
        topic = "user-event-created",
        group = "ktor-user-group",
        bootstrapServers = bootstrapServers,
        schemaUrl = schemaUrl,

        onReceiveRecord = { record ->
            log.info("new record: $record")
            val user = Avro.default.fromRecord(UserConsumeMessageDto.serializer(), record)
            log.info("new register user: $user")
        },

        onError = { record, exception ->
            log.error("sending record to DLQ: $record", exception)
        }
    )
}