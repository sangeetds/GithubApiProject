import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.10"
    application
    kotlin("kapt") version "1.4.10"
}

group = "me.sangeetnarayan"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    val retrofitVersion = "2.9.0"
    val coroutinesVersion = "1.4.1"
    val moshiVersion = "1.11.0"
    val cliktVersion = "3.0.1"

    implementation("com.github.ajalt.clikt:clikt:$cliktVersion")

    implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation("com.squareup.retrofit2:converter-moshi:$retrofitVersion")
    implementation("com.squareup.moshi:moshi-kotlin:$moshiVersion")
    implementation("com.squareup.moshi:moshi:$moshiVersion")
    kapt("com.squareup.moshi:moshi-kotlin-codegen:$moshiVersion")
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "1.8"
}
application {
    mainClassName = "MainKt"
}