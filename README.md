# GoJek Android Assignment

## Description

This application shows the current trending Github repositories fetched from a public API. 

* App is fully functional with managed every states like data loading, success and error with retry button.
* All the repository item is expanded by being tapped and collaped when tap again, at a time only one item will expand.
* App data is services configurration changes
* App have the full offline support. cache data will be shown for the duration of 2 hours.
* App have the pull to refresh for fetch data from remote.
* There are sort by options available for sort repositories by star and name.
* Implemented this app with Koin DI



## Libraries

* [AndroidX](https://developer.android.com/jetpack/androidx/migrate) - Android support library
* androidx.appcompat:appcompat
  * androidx.core:core-ktx
  * androidx.lifecycle:lifecycle-extensions
  * androidx.constraintlayout:constraintlayout


* [Navigation Component](https://developer.android.com/jetpack/androidx/releases/navigation) - For manage fragment navigation
  * androidx.navigation:navigation-fragment-ktx
  * androidx.navigation:navigation-ui-ktx

* [Material Design](https://material.io/develop/android/docs/getting-started/) - Design
  * com.google.android.material:material 

* [Retrofit](https://github.com/square/retrofit) - Type-safe HTTP client
  * com.squareup.retrofit2:retrofit
  * com.squareup.retrofit2:converter-gson
  * com.squareup.okhttp3:logging-interceptor

* [Gson](https://github.com/google/gson) - Java serialization/deserialization library
  * com.google.code.gson:gson

* [Kotlin-Coroutine](https://github.com/Kotlin/kotlinx.coroutines) - concurrent programming
  * org.jetbrains.kotlinx:kotlinx-coroutines-android
  * org.jetbrains.kotlinx:kotlinx-coroutines-test

*  [Glide](https://github.com/bumptech/glide) - An image loading and caching library for Android focused on smooth scrolling 
  * com.github.bumptech.glide:glide
  * com.github.bumptech.glide:compiler

* [Koin](https://github.com/InsertKoinIO/koin) - a pragmatic lightweight dependency injection framework for Kotlin
  * org.koin:koin-android
  * org.koin:koin-androidx-viewmodel
  * org.koin:koin-test

* [mockito](https://github.com/mockito/mockito) - Most popular Mocking framework for unit tests written in Java
  * org.mockito:mockito-core