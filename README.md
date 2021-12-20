# Android Sample

> :warning: **This project requires you to be using JDK 11**: Please make sure to change your JDK version from:
> File -> Project Structure -> SDK Location -> Gradle Settings -> Gradle JDK and choose JDK 11.

## What is this?

This is a very simple Android sample app that demonstrates some of the technology I'm interested in or use on a daily basis. In the future, it'll act as the
basis to test new and interesting Android stuff. The app is made of a couple of screens a Post List screen and a Details Screen.

## Architecture

Since the app is very simple, I decided not to "over-engineer" the architecture. However, I still wanted it to be somewhat reflective of the production code I
usually produce. Therefore, I went with a Single Activity architecture with MVVM.

### Why Single Activity?

It's the recommended approach to use nowadays in Android and ever since the introduction of Jetpack Navigation using Fragments have been easier and easier.
Moreover, I feel testing Fragments with FragmentScenario is easier than Activities because of the FragmentFactory.

### Why MVVM?

Well, when I said just MVVM I lied 😛. It's a mixture of MVVM and MVI. Where the UI rendering is done via a single State flow from the associated ViewModel and
the single actions (e.g. showing toasts or navigation) is done by an Effect flow. This makes UI tests much easier because we can emit whatever state we want and
see how the Fragment behaves. Furthermore, the ViewModels connect to Repositories to handle data fetching and manipulation.

### Why flows?

They're easier to work with than LiveData and/or RxJava, they're built on Kotlin Coroutines which gives them first-party support, and Google has been pushing
them as a replacement on apps built with Kotlin.

## Notable Libraries

* [Kotlin flow](https://developer.android.com/kotlin/flow)
* [Apollo](https://github.com/apollographql/apollo-kotlin/blob/main/docs/source/index.md)
* [Hilt](https://dagger.dev/hilt/)
* [Room](https://developer.android.com/jetpack/androidx/releases/room)
* [Navigation](https://developer.android.com/guide/navigation?gclid=Cj0KCQiA8ICOBhDmARIsAEGI6o0SpL6URzllzI04j6_5VIlVZb5RAZMdNVzHIrAcqkXxrcGKdf7KBZkaAkP6EALw_wcB&gclsrc=aw.ds)
* [Timber](https://github.com/JakeWharton/timber)
* [Turbine](https://github.com/cashapp/turbine)
* [Kotest](https://github.com/kotest/kotest)
