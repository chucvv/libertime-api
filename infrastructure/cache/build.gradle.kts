val lettuceVersion: String by project

plugins {
    kotlin("jvm")
}

kotlin.sourceSets["main"].kotlin.srcDirs("src")
kotlin.sourceSets["test"].kotlin.srcDirs("test")

dependencies {
    implementation(project(":port"))
    implementation("io.lettuce:lettuce-core:$lettuceVersion")
}