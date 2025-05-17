plugins {
    kotlin("jvm")
    kotlin("plugin.spring") version "1.9.20"
    id("org.springframework.boot") version "3.4.3"
    id("io.spring.dependency-management") version "1.1.7"
    id("com.google.devtools.ksp") version "1.9.20-1.0.14"
}

group = "cn.cotenite"
version = "unspecified"

repositories {
    mavenCentral()
}

val jimmerVersion = "0.9.66"
val mysqlConnectVersion = "8.0.30"
val redissonVersion = "3.23.5"
val jacksonVersion = "2.15.0"
val hutoolVersion = "5.8.26"


dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework:spring-context-support:6.2.3")

    implementation("com.fasterxml.jackson.core:jackson-databind:${jacksonVersion}")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:${jacksonVersion}")


    implementation("org.redisson:redisson-spring-boot-starter:${redissonVersion}")

    implementation("org.babyfish.jimmer:jimmer-spring-boot-starter:${jimmerVersion}")

    implementation("cn.hutool:hutool-all:${hutoolVersion}")

    implementation("org.springframework.cloud:spring-cloud-starter-bootstrap:4.2.0")
    implementation("com.alibaba.cloud:spring-cloud-starter-alibaba-nacos-config:2023.0.3.2")
    implementation("com.alibaba.cloud:spring-cloud-starter-alibaba-nacos-discovery:2023.0.3.2")

    implementation("org.apache.dubbo:dubbo:3.3.4")
    implementation("org.apache.dubbo:dubbo-spring-boot-starter:3.3.4")

    implementation("org.apache.rocketmq:rocketmq-spring-boot-starter:2.3.3")

    //seata
    implementation("org.apache.seata:seata-spring-boot-starter:2.3.0")
    implementation("com.alibaba.cloud:spring-cloud-starter-alibaba-seata:2023.0.3.2")



    //项目间依赖管理
    implementation(project(":commons"))
    implementation(project(":id-generator:id-generator-api"))
    implementation(project(":user:user-api"))
    implementation(project(":ratelimter"))


    runtimeOnly("mysql:mysql-connector-java:${mysqlConnectVersion}")

    ksp("org.babyfish.jimmer:jimmer-ksp:${jimmerVersion}")

    testImplementation(kotlin("test"))
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation(project(":Auth:auth-application"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    sourceSets.main {
        kotlin.srcDir("build/generated/ksp/main/kotlin")
    }
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
    jvmToolchain(17)
}