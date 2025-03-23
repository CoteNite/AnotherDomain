plugins {
    kotlin("jvm")
}

group = "cn.cotenite"
version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.google.guava:guava:33.4.0-jre")
    implementation("org.springframework.boot:spring-boot-starter:3.4.3")
    implementation("org.springframework.boot:spring-boot-starter-aop:3.4.3")
    implementation("cn.hutool:hutool-core:5.8.36")

    implementation(project(":commons"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}