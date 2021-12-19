// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {

    repositories {
        google()
        mavenCentral()
        maven(url = "https://plugins.gradle.org/m2/")
    }
    dependencies {
        classpath("com.android.tools.build:gradle:${Versions.androidGradlePlugin}")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}")
        classpath("de.mannodermaus.gradle.plugins:android-junit5:${Versions.jUnit5Android}")
        classpath("com.google.dagger:hilt-android-gradle-plugin:${Versions.dagger}")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.androidXNavigation}")
    }
}

plugins {
    id("org.jlleitschuh.gradle.ktlint") version Versions.ktlintGradle
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven(url = "https://jitpack.io")
        maven(url = "https://plugins.gradle.org/m2/")
    }
    apply(plugin = "org.jlleitschuh.gradle.ktlint") // Version should be inherited from parent
}

configure<org.jlleitschuh.gradle.ktlint.KtlintExtension> {
    android.set(true)
    outputToConsole.set(true)
    outputColorName.set("RED")
    ignoreFailures.set(false)
    enableExperimentalRules.set(true)
    filter {
        exclude("**/generated/**")
        include("**/kotlin/**")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

tasks.register("x", GradleBuild::class) {
    tasks = listOf("ktlintCheck", "lintDebug", "testDebugUnitTest")
}
