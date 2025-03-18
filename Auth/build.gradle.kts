plugins {
    kotlin("jvm")
}

group = "cn.cotenite"
version = "unspecified"

repositories {
    mavenCentral()
}

tasks.test {
    useJUnitPlatform()
}