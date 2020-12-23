val ktorVersion: String by project
val kafkaClients: String by project
val kafkaAvroSerializer: String by project
val kotlinVersion: String by project
val serializationVersion: String by project
val avro4kVersion: String by project

plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
}

kotlin.sourceSets["main"].kotlin.srcDirs("src")
kotlin.sourceSets["test"].kotlin.srcDirs("test")

dependencies {
    implementation(project(":port"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion")
    implementation("io.ktor:ktor-server-core:$ktorVersion")
    implementation("io.confluent:kafka-avro-serializer:$kafkaAvroSerializer")
    implementation("org.apache.kafka:kafka-clients:$kafkaClients")
    implementation("org.apache.kafka:connect-json:$kafkaClients")
    implementation("com.sksamuel.avro4k:avro4k-core:$avro4kVersion")
}