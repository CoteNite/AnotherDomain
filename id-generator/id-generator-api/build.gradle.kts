plugins {
    java
    kotlin("jvm")
}

group = "cn.cotenite"
version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {

}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

kotlin {
    jvmToolchain(17)
}