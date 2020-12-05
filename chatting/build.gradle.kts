val ktorVersion: String by project
val koinVersion: String by project
val gsonVersion: String by project
plugins {
    kotlin("jvm")
}

kotlin.sourceSets["main"].kotlin.srcDirs("src")
kotlin.sourceSets["test"].kotlin.srcDirs("test")

dependencies {
    implementation("org.koin:koin-core:$koinVersion")
    implementation("org.koin:koin-ktor:$koinVersion")

    implementation("com.google.code.gson:gson:$gsonVersion")
    implementation("com.google.code.gson:gson-extras:$gsonVersion")

    implementation("io.ktor:ktor-websockets:$ktorVersion")
}