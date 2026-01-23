plugins {
    alias(libs.plugins.kotlin.jvm)
}

group = "cn.enaium.cafmod"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.asm.tree)
    implementation(libs.asm.analysis)
    implementation(libs.asm.commons)
    implementation(libs.asm.util)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.jackson)
    testImplementation(kotlin("test"))
}

kotlin {
    jvmToolchain(8)
}

tasks.test {
    useJUnitPlatform()
}