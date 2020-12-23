val ktorVersion: String by project
val exposedVersion: String by project
val koinVersion: String by project
val bcryptVersion: String by project
val lettuceVersion: String by project
val hikariCpVersion: String by project
val kotlinLogging: String by project
val avro4kVersion: String by project
val kafkaClients: String by project
plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
}

kotlin.sourceSets["main"].kotlin.srcDirs("src")
kotlin.sourceSets["test"].kotlin.srcDirs("test")

dependencies {
    implementation(project(":port"))
    implementation(project(":common"))
    implementation(project(":database"))
    implementation(project(":server-config"))
    implementation(project(":kafka"))
    implementation(project(":chatting"))

    implementation("io.ktor:ktor-locations:$ktorVersion")
    implementation("io.ktor:ktor-jackson:$ktorVersion")

    implementation("io.ktor:ktor-auth:$ktorVersion")

    implementation("io.lettuce:lettuce-core:$lettuceVersion")

    implementation("org.jetbrains.exposed:exposed-dao:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jodatime:$exposedVersion")

    implementation("org.koin:koin-core:$koinVersion")
    implementation("org.koin:koin-ktor:$koinVersion")

    implementation("io.ktor:ktor-auth:$ktorVersion")
    implementation("io.ktor:ktor-auth-jwt:$ktorVersion")
    implementation( "io.ktor:ktor-websockets:$ktorVersion")
    implementation("com.zaxxer:HikariCP:$hikariCpVersion")
    implementation("org.mindrot:jbcrypt:$bcryptVersion")
    implementation("io.github.microutils:kotlin-logging:${kotlinLogging}")

    implementation("com.sksamuel.avro4k:avro4k-core:$avro4kVersion")
    implementation("org.apache.kafka:kafka-clients:$kafkaClients")
}