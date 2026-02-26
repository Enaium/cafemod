plugins {
    alias(libs.plugins.kotlin.jvm)
}

group = "cn.enaium.cafmod"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    api(libs.asm.tree)
    api(libs.asm.analysis)
    api(libs.asm.commons)
    api(libs.asm.util)
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