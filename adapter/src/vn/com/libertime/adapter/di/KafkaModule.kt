package vn.com.libertime.adapter.di

import org.koin.core.module.Module
import org.koin.dsl.module
import vn.com.libertime.adapter.client_side.um.kafka.KafkaProducerAdapter
import vn.com.libertime.adapter.client_side.um.kafka.UserNotificationAdapter
import vn.com.libertime.kafka.producer.producer
import vn.com.libertime.port.um.required.EnvironmentProvidable
import vn.com.libertime.port.um.required.UserNoticeable

public object KafkaModule {
    private val userKafkaModule = module {
        single { UserNotificationAdapter(get(), get()) as UserNoticeable }
        single {
            val producerConfig by inject<EnvironmentProvidable>()
            producerConfig.producerKafkaConfig.run {
                return@single KafkaProducerAdapter(
                    producer = producer(
                        bootstrapServers = bootstrapServers,
                        schemaUrl = schemaUrl
                    )
                )
            }
        }
    }

    public val dependencies: List<Module> = listOf(userKafkaModule)
}