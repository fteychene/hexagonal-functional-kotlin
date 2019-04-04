import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.3.21"
    kotlin("kapt") version "1.3.21"
}

group = "com.fteychene.demo"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

val arrowVersion = "0.9.0"

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    compile(project(":domain"))

    compile("io.arrow-kt:arrow-core-data:$arrowVersion")
    compile("io.arrow-kt:arrow-core-extensions:$arrowVersion")
    compile("io.arrow-kt:arrow-syntax:$arrowVersion")
    compile("io.arrow-kt:arrow-typeclasses:$arrowVersion")
    compile("io.arrow-kt:arrow-extras-data:$arrowVersion")
    compile("io.arrow-kt:arrow-extras-extensions:$arrowVersion")
    kapt("io.arrow-kt:arrow-meta:$arrowVersion")
    compile("io.arrow-kt:arrow-optics:$arrowVersion")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}