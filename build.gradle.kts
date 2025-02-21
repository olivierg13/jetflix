import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import com.github.benmanes.gradle.versions.updates.gradle.GradleReleaseChannel
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import com.android.build.gradle.BaseExtension

buildscript {
    repositories {
        google()
        mavenCentral()
        maven("https://plugins.gradle.org/m2/")
    }
    dependencies {
        classpath(Dependencies.Gradle.androidBuildPlugin)
        classpath(Dependencies.Gradle.hiltPlugin)
        classpath(Dependencies.Gradle.kotlinPlugin)
        classpath(Dependencies.Gradle.kotlinSerializationPlugin)
    }
}

plugins {
   id(Dependencies.Gradle.VersionsPlugin.id) version Dependencies.Gradle.VersionsPlugin.version
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "plugins.ktlint")
    afterEvaluate {
        tasks.withType(KotlinCompile::class).all {
            kotlinOptions {
                jvmTarget = JavaVersion.VERSION_11.toString()
                allWarningsAsErrors = true
                freeCompilerArgs = listOf("-Xopt-in=kotlin.RequiresOptIn", "-Xjvm-default=all")
            }
        }

        extensions.findByType<BaseExtension>() ?: return@afterEvaluate
        configure<BaseExtension> {
            compileSdkVersion(Versions.compileSdk)
            defaultConfig {
                minSdk = Versions.minSdk
                targetSdk = Versions.targetSdk
                versionName = Versions.versionName
                versionCode = Versions.versionCode
                testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
            }
            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_11
                targetCompatibility = JavaVersion.VERSION_11
            }
        }
    }
}

tasks.named<DependencyUpdatesTask>("dependencyUpdates").configure {
    checkForGradleUpdate = true
    gradleReleaseChannel = GradleReleaseChannel.RELEASE_CANDIDATE.id
    revision = "integration"
    outputFormatter = "plain"
    outputDir = "build/dependencyUpdates"
    reportfileName = "dependency_update_report"
}

tasks.withType<Test>().configureEach {
    reports.html.required.set(false)
    reports.junitXml.required.set(true)
    maxParallelForks = Runtime.getRuntime().availableProcessors()
}

// Change gradleVersion and run gradlew wrapper to properly update gradle wrapper
tasks.named<Wrapper>("wrapper") {
    distributionType = Wrapper.DistributionType.BIN
    gradleVersion = "7.3-rc-5"
}

task("clean", Delete::class) {
    delete(rootProject.buildDir)
}
