import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.3.21"
    kotlin("kapt") version "1.3.21"
}

group = parent!!.group
version = parent!!.version

repositories {
    mavenCentral()
}

val arrowVersion = "0.9.0"

dependencies {
    implementation(kotlin("stdlib"))

    compile("io.arrow-kt:arrow-core-data:$arrowVersion")
    compile("io.arrow-kt:arrow-core-extensions:$arrowVersion")
    compile("io.arrow-kt:arrow-syntax:$arrowVersion")
    compile("io.arrow-kt:arrow-typeclasses:$arrowVersion")
    compile("io.arrow-kt:arrow-extras-data:$arrowVersion")
    compile("io.arrow-kt:arrow-extras-extensions:$arrowVersion")
    kapt("io.arrow-kt:arrow-meta:$arrowVersion")

    compile("io.arrow-kt:arrow-effects-data:$arrowVersion")
    compile("io.arrow-kt:arrow-effects-extensions:$arrowVersion")
    compile("io.arrow-kt:arrow-effects-io-extensions:$arrowVersion")
    compile("io.arrow-kt:arrow-optics:$arrowVersion")
    compile("io.arrow-kt:arrow-effects-rx2-data:$arrowVersion")
    compile("io.arrow-kt:arrow-effects-rx2-extensions:$arrowVersion")
    compile("io.reactivex.rxjava2:rxjava:2.2.8")
    compile("io.arrow-kt:arrow-effects-reactor-data:$arrowVersion")
    compile("io.arrow-kt:arrow-effects-reactor-extensions:$arrowVersion")
    compile("io.projectreactor:reactor-core:3.2.8.RELEASE")


    testCompile("junit", "junit", "4.12")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}
tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}