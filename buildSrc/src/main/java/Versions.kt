object Versions {
    private const val versionMajor = 1
    private const val versionMinor = 0
    private const val versionPatch = 0
    private const val versionBuild = 0
    const val appVersionName = "$versionMajor.$versionMinor.$versionPatch"
    const val appVersionCode = 1000000 + 1000 * versionMajor + 100 * versionMinor + 10 * versionPatch + versionBuild

    // Top level
    const val androidGradlePlugin = "7.0.4"
    const val ktlintGradle = "10.0.0" // https://github.com/jlleitschuh/ktlint-gradle

    // Android
    const val androidMinSdk = 21
    const val androidTargetSdk = 31
    const val androidCompileSdk = 31

    // Kotlin
    const val kotlin = "1.6.10"
    const val kotlinCoroutines = "1.6.0-RC3"

    // AndroidX and other Google things
    const val androidXCore = "1.6.0"
    const val androidXFragment = "1.3.6"
    const val androidXNavigation = "2.3.5"
    const val androidXRecyclerView = "1.2.1"
    const val androidXCardView = "1.0.0"
    const val androidXAppCompat = "1.3.1"
    const val androidXVectorDrawableAnimated = "1.0.0"
    const val androidXConstraintLayout = "2.1.1"
    const val androidXLifecycle = "2.3.1"
    const val androidXArch = "2.1.0"
    const val androidXPaging = "3.0.1"
    const val androidXAnnotation = "1.1.0"
    const val swipeRefresh = "1.1.0"

    // Libraries
    const val googleMaterialComponents = "1.4.0"
    const val room = "2.3.0"
    const val dagger = "2.40.5"
    const val gson = "2.8.8"
    const val okHttp = "4.9.1"
    const val timber = "5.0.1"
    const val leakCanary = "2.7"
    const val apollo = "3.0.0"
    const val okHttp3 = "4.9.3"

    // Testing
    const val jUnit5 = "5.7.1"
    const val jUnit5Android = "1.7.1.1"
    const val kotest = "4.6.3"
    const val androidXTest = "1.4.0"
    const val androidXTestEspresso = "3.4.0"
    const val androidXTestJUnit = "1.1.3"
    const val androidXTestOrchestrator = "1.4.0"
    const val androidXFragmentTesting = "1.4.0"
    const val mockk = "1.12.0"
    const val turbine = "0.6.1"
}
