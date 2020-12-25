val exposedVersion: String by project
val postgresqlVersion: String by project
val postgisJdbcVersion: String by project
plugins {
    kotlin("jvm")
}

kotlin.sourceSets["main"].kotlin.srcDirs("src")
kotlin.sourceSets["test"].kotlin.srcDirs("test")

dependencies {
    implementation(project(":port"))
    implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-dao:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jodatime:$exposedVersion")

    implementation("org.postgresql:postgresql:$postgresqlVersion")
    implementation("net.postgis:postgis-jdbc:$postgisJdbcVersion") {
        exclude(module = "postgresql")
    }
}