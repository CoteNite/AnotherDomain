plugins {
    kotlin("jvm")
}

group = "cn.cotenite"
version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":Auth:auth-domain"))
}

tasks.test {
    useJUnitPlatform()
}