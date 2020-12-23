package vn.com.libertime.adapter.client_side.um.kafka

import org.apache.avro.generic.GenericRecord
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.clients.producer.RecordMetadata
import vn.com.libertime.kafka.producer.dispatch

public class KafkaProducerAdapter(private val producer: KafkaProducer<String, GenericRecord>) {

    public suspend fun send(
        topicName: String,
        record: ProducerRecord<String, GenericRecord>
    ): RecordMetadata =
        producer.dispatch(record)
}