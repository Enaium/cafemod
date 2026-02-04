plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.shadow)
}

group = "cn.enaium.cafemod"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":core"))
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.jackson)
}

kotlin {
    jvmToolchain(8)
}

tasks.register<Exec>("runFlutter") {
    workingDir("src/main/flutter")
    commandLine("flutter.bat", "run")
    dependsOn(tasks.build)
}

tasks.assemble {
    dependsOn("shadowJar")
}

tasks.shadowJar {
    archiveFileName.set("lib.jar")
}