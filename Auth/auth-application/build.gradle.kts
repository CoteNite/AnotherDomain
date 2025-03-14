plugins {
    kotlin("jvm")
    kotlin("plugin.spring") version "1.9.25"
    id("org.springframework.boot") version "3.4.3"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "cn.cotenite"
version = "0.0.1"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

val jimmerVersion = "0.9.37"
val mysqlConnectVersion = "8.0.30"
val redissonVersion = "3.23.5"
val jacksonVersion = "2.15.0"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-test")

    implementation("com.fasterxml.jackson.core:jackson-databind:${jacksonVersion}")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:${jacksonVersion}") // Kotlin 支持
    implementation("org.redisson:redisson-spring-boot-starter:${redissonVersion}")
    implementation("org.babyfish.jimmer:jimmer-spring-boot-starter:${jimmerVersion}")

    runtimeOnly("mysql:mysql-connector-java:${mysqlConnectVersion}")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}


tasks.withType<Test> {
    useJUnitPlatform()
}
