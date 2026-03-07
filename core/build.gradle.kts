plugins {
    id("java-library")
    alias(libs.plugins.jetbrains.kotlin.jvm)
    kotlin("kapt")
}
java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}
kotlin {
    compilerOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11
    }
}
dependencies {
    implementation("javax.inject:javax.inject:1")
    implementation("com.google.dagger:dagger:${libs.versions.hilt.get()}")
    kapt("com.google.dagger:dagger-compiler:${libs.versions.hilt.get()}")
}
