pluginManagement {
    plugins {
        kotlin("jvm") version "2.1.10"
    }
}
dependencyResolutionManagement {
    @Suppress("UnstableApiUsage")
    repositories {
        mavenLocal()
        maven {
            setUrl("https://maven.aliyun.com/repository/public/")
        }
        maven {
            setUrl("https://mirrors.huaweicloud.com/repository/maven/")
        }
        mavenCentral()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}



rootProject.name = "AnotherDomain"
include("commons")
include("Auth")
include("Auth:auth-application")
findProject(":Auth:auth-application")?.name = "auth-application"
