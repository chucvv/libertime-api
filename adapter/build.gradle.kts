val ktorVersion: String by project
val exposedVersion: String by project
val koinVersion: String by project
val bcryptVersion: String by project
val lettuceVersion: String by project
val hikariCpVersion: String by project

plugins {
    kotlin("jvm")
}

kotlin.sourceSets["main"].kotlin.srcDirs("src")
kotlin.sourceSets["test"].kotlin.srcDirs("test")

dependencies {
    implementation(project(":database"))
    implementation(project(":port"))
    implementation(project(":common"))

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

    implementation("com.zaxxer:HikariCP:$hikariCpVersion")

    implementation("org.mindrot:jbcrypt:$bcryptVersion")
}