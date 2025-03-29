pluginManagement {
    plugins {
        kotlin("jvm") version "1.9.20"
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
include("id-generator")
include("id-generator:id-generator-core")
findProject(":id-generator:id-generator-core")?.name = "id-generator-core"
include("id-generator:id-generator-api")
findProject(":id-generator:id-generator-api")?.name = "id-generator-api"
include("gateway")
include("ratelimter")
include("oss")
include("oss:oss-api")
findProject(":oss:oss-api")?.name = "oss-api"
include("oss:oss-core")
findProject(":oss:oss-core")?.name = "oss-core"
include("user")
include("user:user-core")
findProject(":user:user-core")?.name = "user-core"
include("user:user-api")
findProject(":user:user-api")?.name = "user-api"
include("kv")
include("kv:kv-core")
findProject(":kv:kv-core")?.name = "kv-core"
include("kv:kv-api")
findProject(":kv:kv-api")?.name = "kv-api"
include("note")
include("note:note-core")
findProject(":note:note-core")?.name = "note-core"
