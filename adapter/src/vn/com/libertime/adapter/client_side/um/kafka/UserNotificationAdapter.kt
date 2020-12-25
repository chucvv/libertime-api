package vn.com.libertime.adapter.client_side.um.kafka

import com.sksamuel.avro4k.Avro
import org.apache.kafka.clients.producer.ProducerRecord
import vn.com.libertime.common.log.Logger
import vn.com.libertime.port.um.entity.UserEvent
import vn.com.libertime.port.um.required.UserNoticeable

public class UserNotificationAdapter(
    private val producer: KafkaProducerAdapter,
    private val logger: Logger
) : UserNoticeable {

    private val topicNameToNotify: String = "user-event-created"

    override suspend fun notify(userEvent: UserEvent) {

        logger.info("notifying event: $userEvent")
        val eventDto = userEvent.toMessageDTO()

        val avroSchema = Avro.default.toRecord(UserMessageDTO.serializer(), eventDto)
        val record = ProducerRecord(topicNameToNotify, eventDto.id, avroSchema)
        producer.send(topicName = topicNameToNotify, record = record)

        logger.info("event published successfully")
    }
}