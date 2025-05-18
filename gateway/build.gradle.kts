plugins {
    kotlin("jvm")
    kotlin("plugin.spring") version "1.9.20"
    id("org.springframework.boot") version "3.4.3"
}

group = "cn.cotenite"
version = "0.0.1"

val satokenVersion = "1.40.0"
val redissonVersion = "3.23.5"


dependencies {
    //外部依赖
    implementation("org.springframework.boot:spring-boot-starter-web:3.4.5")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.19.0")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.springframework:spring-context-support:6.2.3")
    implementation("org.springframework.cloud:spring-cloud-starter-bootstrap:4.2.0")
    implementation("com.alibaba.cloud:spring-cloud-starter-alibaba-nacos-config:2023.0.3.2")
    implementation("com.alibaba.cloud:spring-cloud-starter-alibaba-nacos-discovery:2023.0.3.2")
    implementation ("org.springframework.cloud:spring-cloud-starter-gateway:4.2.0")
    implementation("org.springframework.cloud:spring-cloud-starter-loadbalancer:4.2.0")
    implementation("com.alibaba.cloud:spring-cloud-starter-alibaba-sentinel:2023.0.3.1")
    implementation("com.alibaba.csp:sentinel-datasource-nacos:1.8.8")
    implementation("com.alibaba.csp:sentinel-spring-cloud-gateway-adapter:1.8.8")
    implementation("org.aspectj:aspectjweaver:1.9.20.1")
    implementation("com.google.guava:guava:33.4.0-jre")
    implementation("org.redisson:redisson-spring-boot-starter:${redissonVersion}")

    implementation("com.github.ben-manes.caffeine:caffeine:3.2.0")


    implementation("cn.hutool:hutool-all:5.8.20")

    implementation("cn.dev33:sa-token-redis-jackson:${satokenVersion}")
    implementation("cn.dev33:sa-token-reactor-spring-boot3-starter:${satokenVersion}")
    implementation("org.apache.commons:commons-pool2:2.12.0")

    implementation(project(":commons"))

    testImplementation(kotlin("test"))
    testImplementation("org.springframework.boot:spring-boot-starter-test:3.4.5")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher:1.12.2")
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
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}


tasks.withType<Test> {
    useJUnitPlatform()
}
