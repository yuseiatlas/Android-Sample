// To avoid compatibility issues at runtime
if (JavaVersion.current() < JavaVersion.VERSION_11) {
    throw GradleException("This project requires at least Java 11, but it's running on " + JavaVersion.current())
}

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("kotlin-android-extensions") // Needs to be after kotlin-android
    id("dagger.hilt.android.plugin")
    id("com.apollographql.apollo3") version Versions.apollo
    id("androidx.navigation.safeargs.kotlin")
}
android {
    compileSdk = Versions.androidCompileSdk

    defaultConfig {
        applicationId = "com.example.androidsample"
        minSdk = Versions.androidMinSdk
        targetSdk = Versions.androidTargetSdk
        versionCode = Versions.appVersionCode
        versionName = Versions.appVersionName

        buildConfigField("String", "SERVER_URL", "\"https://graphqlzero.almansi.me/api\"")
        buildConfigField("String", "DATABASE_NAME", "\"sample_db\"")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }

    kapt {
        correctErrorTypes = true
    }

    buildFeatures {
        viewBinding = true
    }

    lint {
        isCheckReleaseBuilds = false
        isAbortOnError = false // this must be removed in the future!
    }

    testOptions {
        unitTests.isIncludeAndroidResources = true
        unitTests.isReturnDefaultValues = true
        unitTests.all {
            it.useJUnitPlatform()
        }
    }

    hilt {
        enableAggregatingTask = true
    }

    apollo {
        packageName.set(defaultConfig.applicationId)
    }

    packagingOptions {
        resources.excludes.add("/META-INF/AL2.0")
        resources.excludes.add("/META-INF/LGPL2.1")
    }
}

dependencies {
    // Kotlin
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlinCoroutines}")

    // AndroidX
    implementation("androidx.core:core-ktx:${Versions.androidXCore}")
    implementation("androidx.fragment:fragment:${Versions.androidXFragment}")
    implementation("androidx.fragment:fragment-ktx:${Versions.androidXFragment}")
    implementation("androidx.constraintlayout:constraintlayout:${Versions.androidXConstraintLayout}")
    implementation("androidx.recyclerview:recyclerview:${Versions.androidXRecyclerView}")
    implementation("androidx.cardview:cardview:${Versions.androidXCardView}")
    implementation("androidx.appcompat:appcompat:${Versions.androidXAppCompat}")
    implementation("androidx.navigation:navigation-fragment-ktx:${Versions.androidXNavigation}")
    implementation("androidx.navigation:navigation-ui-ktx:${Versions.androidXNavigation}")
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:${Versions.swipeRefresh}")

    // Lifecycle
    implementation("androidx.lifecycle:lifecycle-common-java8:${Versions.androidXLifecycle}")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:${Versions.androidXLifecycle}")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.androidXLifecycle}")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:${Versions.androidXLifecycle}")

    // Google
    implementation("com.google.android.material:material:${Versions.googleMaterialComponents}")

    // Room
    kapt("androidx.room:room-compiler:${Versions.room}")
    implementation("androidx.room:room-runtime:${Versions.room}")
    implementation("androidx.room:room-ktx:${Versions.room}")

    // Dagger
    implementation("com.google.dagger:hilt-android:${Versions.dagger}")
    kapt("com.google.dagger:hilt-compiler:${Versions.dagger}")

    // Standard libraries
    implementation("com.jakewharton.timber:timber:${Versions.timber}")
    implementation("com.apollographql.apollo3:apollo-runtime:${Versions.apollo}")
    implementation("com.squareup.okhttp3:logging-interceptor:${Versions.okHttp3}")

    // **** DEBUG **** //

    debugImplementation("androidx.fragment:fragment-testing:${Versions.androidXFragmentTesting}")

    // **** TESTING **** //

    // (Required) Writing and executing Unit Tests on the JUnit Platform
    testImplementation("org.junit.jupiter:junit-jupiter-api:${Versions.jUnit5}")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:${Versions.jUnit5}")

    // Dagger
    androidTestImplementation("com.google.dagger:hilt-android-testing:${Versions.dagger}")
    kaptAndroidTest("com.google.dagger:hilt-compiler:${Versions.dagger}")

    // Kotest
    testImplementation("io.kotest:kotest-runner-junit5:${Versions.kotest}")
    testImplementation("io.kotest:kotest-assertions-core:${Versions.kotest}")
    androidTestImplementation("io.kotest:kotest-assertions-core:${Versions.kotest}")

    // AndroidX Core testing
    androidTestImplementation("androidx.test:core:${Versions.androidXTest}")
    androidTestImplementation("androidx.test:runner:${Versions.androidXTest}")
    androidTestImplementation("androidx.test.ext:junit:${Versions.androidXTestJUnit}")
    androidTestImplementation("androidx.test:rules:${Versions.androidXTest}")
    androidTestUtil("androidx.test:orchestrator:${Versions.androidXTestOrchestrator}")

    // AndroidX testing
    testImplementation("androidx.arch.core:core-testing:${Versions.androidXArch}")
    testImplementation("androidx.room:room-testing:${Versions.room}") // optional - Test helpers
    androidTestImplementation("androidx.arch.core:core-testing:${Versions.androidXArch}")
    androidTestImplementation("androidx.annotation:annotation:${Versions.androidXAnnotation}")
    androidTestImplementation("androidx.room:room-testing:${Versions.room}") // optional - Test helpers

    // Coroutine testing
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.kotlinCoroutines}")
    androidTestImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.kotlinCoroutines}")
    testImplementation("app.cash.turbine:turbine:${Versions.turbine}")

    // Optional -- UI testing with Espresso
    androidTestImplementation("androidx.test.espresso:espresso-core:${Versions.androidXTestEspresso}")
    androidTestImplementation("androidx.test.espresso:espresso-intents:${Versions.androidXTestEspresso}")
    androidTestImplementation("androidx.test.espresso:espresso-contrib:${Versions.androidXTestEspresso}")

    // Mockk
    testImplementation("io.mockk:mockk:${Versions.mockk}")
    androidTestImplementation("io.mockk:mockk-android:${Versions.mockk}")
}
