plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.shadow)
    application
}

group = "cn.enaium.cafemod"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://jitpack.io")
    maven("https://repo.casterlabs.co/maven")
}

dependencies {
    implementation(project(":core"))
    implementation(libs.saucer.api)
    implementation(libs.saucer.all)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.jackson)
}

kotlin {
    jvmToolchain(11)
}

application {
    mainClass = "cn.enaium.cafemod.MainKt"
    applicationDefaultJvmArgs += "-Ddebug=true"
}

tasks.register<Exec>("bunRun") {
    group = "application"
    workingDir("src/main/vue")
    commandLine("bun", "run", "dev")
}

tasks.register<Exec>("bunBuild") {
    group = "build"
    workingDir("src/main/vue")
    commandLine("bun", "run", "build")
}

tasks.jar {
    dependsOn("bunBuild")
}

tasks.assemble {
    dependsOn("shadowJar")
}