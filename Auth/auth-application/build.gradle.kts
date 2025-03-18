plugins {
    kotlin("jvm")
    kotlin("plugin.spring") version "2.1.10"
    id("org.springframework.boot") version "3.4.3"
    id("io.spring.dependency-management") version "1.1.7"
    id("com.google.devtools.ksp") version "2.1.10-1.0.31"
}

group = "cn.cotenite"
version = "0.0.1"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

kotlin{
    sourceSets.main {
        kotlin.srcDir("build/generated/ksp/main/kotlin")
    }
}


val jimmerVersion = "0.9.66"
val mysqlConnectVersion = "8.0.30"
val redissonVersion = "3.23.5"
val jacksonVersion = "2.15.0"
val hutoolVersion = "5.8.26"
val bcryptVersion = "6.2.0"
val satokenVersion = "1.40.0"


dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.springframework.boot:spring-boot-starter-mail")
    implementation("org.springframework.boot:spring-boot-starter-validation")

    implementation("com.fasterxml.jackson.core:jackson-databind:${jacksonVersion}")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:${jacksonVersion}")
    implementation("org.redisson:redisson-spring-boot-starter:${redissonVersion}")
    implementation("org.babyfish.jimmer:jimmer-spring-boot-starter:${jimmerVersion}")
    implementation("cn.hutool:hutool-all:${hutoolVersion}")
    implementation("cn.dev33:sa-token-redis-jackson:${satokenVersion}")
    implementation("cn.dev33:sa-token-spring-boot3-starter:${satokenVersion}")
    implementation("org.springframework.security:spring-security-crypto:${bcryptVersion}")


    runtimeOnly("mysql:mysql-connector-java:${mysqlConnectVersion}")

    ksp("org.babyfish.jimmer:jimmer-ksp:${jimmerVersion}")

    implementation(project(":commons"))

    testImplementation(kotlin("test"))
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
