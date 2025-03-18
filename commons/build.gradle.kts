plugins {
    kotlin("jvm")
    id("com.google.devtools.ksp") version "2.1.10-1.0.31"
}

group = "cn.cotenite"
version = "unspecified"

repositories {
    mavenCentral()
}


val hutoolVersion = "5.8.26"
val kotlinLoginVersion = "2.0.6"
val jimmerVersion = "0.9.66"

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("io.github.microutils:kotlin-logging-jvm:${kotlinLoginVersion}")
    implementation("cn.hutool:hutool-all:${hutoolVersion}")
    implementation("javax.validation:validation-api:2.0.1.Final")
    implementation("org.babyfish.jimmer:jimmer-spring-boot-starter:${jimmerVersion}")

    ksp("org.babyfish.jimmer:jimmer-ksp:${jimmerVersion}")
}

kotlin {
    jvmToolchain(17)
    sourceSets.main {
        kotlin.srcDir("build/generated/ksp/main/kotlin")
    }
}
