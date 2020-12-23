import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    base
    kotlin("jvm") version "1.4.10" apply false
}

buildscript{
    dependencies{
        classpath("org.jetbrains.kotlin:kotlin-serialization:1.4.10")
    }
}

allprojects {
    group = "vn.com.libertime"
    version = "0.0.1"

    repositories {
        mavenLocal()
        jcenter()
        maven { url = uri("https://kotlin.bintray.com/ktor") }
        maven { url = uri("https://plugins.gradle.org/m2/") }
        maven { url = uri("https://artifactory.cronapp.io/public-release/") }
        maven { url = uri("https://packages.confluent.io/maven/") }
    }

    tasks.withType<Test> {
        useJUnitPlatform {
            includeEngines("junit-jupiter")
        }

        failFast = true
    }
}

// Configure all KotlinCompile tasks on each sub-project
subprojects {
    tasks.withType<KotlinCompile>().configureEach {
        println("Configuring $name in project ${project.name}...")
        kotlinOptions {
            freeCompilerArgs = freeCompilerArgs + "-Xexplicit-api=strict" // or '-Xexplicit-api=warning'
            suppressWarnings = true
            jvmTarget = JavaVersion.VERSION_1_8.toString()
        }
    }
}