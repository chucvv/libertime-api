package vn.com.libertime.adapter.client_side.um.kafka

import vn.com.libertime.common.log.Logger
import vn.com.libertime.kafka.consumer.clientConsumer

internal suspend fun consumerRegisterUser(
    bootstrapServers: String,
    schemaUrl: String,
    log: Logger
) {
    clientConsumer(
        topic = "user-command-request",
        group = "ktor-user-group",
        bootstrapServers = bootstrapServers,
        schemaUrl = schemaUrl,

        onReceiveRecord = { record ->
            log.info("new record: $record")
        },

        onError = { record, exception ->
            log.error("sending record to DLQ: $record", exception)
        }
    )
}