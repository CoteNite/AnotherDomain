dependencyResolutionManagement {

    // Use Maven Central and the Gradle Plugin Portal for resolving dependencies in the shared build logic (`buildSrc`) project.
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

    // Reuse the version catalog from the main build.
    versionCatalogs {
        create("libs") {
            from(files("../gradle/libs.versions.toml"))
        }
    }
}

rootProject.name = "buildSrc"