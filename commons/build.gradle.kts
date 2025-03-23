plugins {
    kotlin("jvm")
    id("com.google.devtools.ksp") version "1.9.20-1.0.14"
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
    implementation("org.springframework.boot:spring-boot-starter:3.4.3")
    implementation("io.github.microutils:kotlin-logging-jvm:${kotlinLoginVersion}")
    implementation("cn.hutool:hutool-all:${hutoolVersion}")
    implementation("javax.validation:validation-api:2.0.1.Final")

    ksp("org.babyfish.jimmer:jimmer-ksp:${jimmerVersion}")
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

kotlin {
    sourceSets.main {
        kotlin.srcDir("build/generated/ksp/main/kotlin")
    }
}
