plugins {
    java
    kotlin("jvm")
    kotlin("plugin.spring") version "1.9.20"
    id("org.springframework.boot") version "3.4.3"
}

group = "cn.cotenite"
version = "0.0.1"

val commonIoVersion by extra("2.4")
val perf4jVersion by extra("0.9.16")
val druidVersion by extra("1.2.8")
val mybatisVersion by extra("3.3.0")
val curatorRecipesVersion by extra("2.6.0")
val zookeeperVersion by extra("3.6.0")


dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web:3.4.5")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.19.0")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    implementation("commons-io:commons-io:$commonIoVersion")
    implementation("org.perf4j:perf4j:$perf4jVersion")
    implementation("mysql:mysql-connector-java:8.0.29")
    implementation("com.alibaba:druid:$druidVersion")
    implementation("org.mybatis:mybatis:$mybatisVersion")
    implementation("org.springframework.cloud:spring-cloud-starter-bootstrap:4.2.0")
    implementation("com.alibaba.cloud:spring-cloud-starter-alibaba-nacos-config:2023.0.3.2")
    implementation("com.alibaba.cloud:spring-cloud-starter-alibaba-nacos-discovery:2023.0.3.2")
    implementation("org.apache.dubbo:dubbo:3.3.4")
    implementation("org.apache.dubbo:dubbo-spring-boot-starter:3.3.4")
    implementation("org.apache.zookeeper:zookeeper:$zookeeperVersion")
    implementation("org.springframework:spring-context-support:6.2.3")
    implementation("org.apache.curator:curator-recipes:$curatorRecipesVersion") {
        exclude(group = "log4j", module = "log4j")
        exclude(group = "org.apache.zookeeper")
    }

    implementation("com.alibaba.cloud:spring-cloud-starter-alibaba-sentinel:2023.0.3.2")
    implementation("com.alibaba.csp:sentinel-datasource-nacos:1.8.8")
    implementation("com.alibaba.csp:sentinel-web-servlet:1.8.8")
    implementation("com.alibaba.cloud:spring-cloud-alibaba-sentinel-gateway:2023.0.3.2")

    implementation(project(":id-generator:id-generator-api"))
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
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}


tasks.withType<Test> {
    useJUnitPlatform()
}
