plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.shadow)
    application
}

repositories {
    mavenCentral()
    google()
}

application {
    mainClass = "cn.enaium.cafemod.MainKt"
}

kotlin {
    jvmToolchain(8)
}

dependencies {
    implementation(project(":core"))
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.imgui)
    implementation(libs.koin)
    implementation(libs.filekit.core)
    implementation(libs.filekit.dialogs)
}

tasks.assemble {
    dependsOn("shadowJar")
}