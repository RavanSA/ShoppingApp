# Shopping Application

![Min API](https://img.shields.io/badge/API-21%2B-orange.svg?style=flat)
[![Platform](https://img.shields.io/badge/platform-Android-green.svg)](http://developer.android.com/index.html)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](https://opensource.org/licenses/MIT)

Android offline first shopping app built with MVVM Architecture using Fake Store API


## Contents
* [Demonstration](#demonstration)
  * [Screenshots](#screenshots)
* [Tech Stack and whys](#tech-stack)
* [Architecture](#architecture)
* [Download](#download)
* [Contribution](#contribution)
* [Questions](#questions)


## Demonstration


### Screenshots

On Boarding Screen 1       | On Boarding Screen 2      | On Boarding Screen 3 
:-------------------------:|:-------------------------:|:-------------------------:
<img src="https://raw.githubusercontent.com/RavanSA/ShoppingApp/master/demo/photo1692390802%20(1).jpeg"/>            |  <img src="https://raw.githubusercontent.com/RavanSA/ShoppingApp/master/demo/photo1692390802%20(2).jpeg"/>  | <img src="https://raw.githubusercontent.com/RavanSA/ShoppingApp/master/demo/photo1692390802%20(3).jpeg"/>  |

Login Screen               | Registration Screen       | Products Screen
:-------------------------:|:-------------------------:|:-------------------------:
<img src="https://raw.githubusercontent.com/RavanSA/ShoppingApp/master/demo/photo1692390802%20(5).jpeg"/>           |  <img src="https://raw.githubusercontent.com/RavanSA/ShoppingApp/master/demo/photo1692390802%20(4).jpeg"/>              | <img src="https://raw.githubusercontent.com/RavanSA/ShoppingApp/master/demo/Screenshot%202023-08-18%20at%2023.29.46.png"/>  |

Basket Screen              | Search Screen             | Detail Screen
:-------------------------:|:-------------------------:|:-------------------------:
<img src="https://raw.githubusercontent.com/RavanSA/ShoppingApp/master/demo/photo1692390802%20(8).jpeg"/>           |  <img src="https://raw.githubusercontent.com/RavanSA/ShoppingApp/master/demo/photo1692390802%20(9).jpeg"/>              | <img src="https://raw.githubusercontent.com/RavanSA/ShoppingApp/master/demo/photo1692390802%20(10).jpeg"/>  |

## Tech Stack and whys

- [Kotlin](https://kotlinlang.org/) - First class and official programming language for Android development.
- [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) and [Flow](https://kotlinlang.org/docs/reference/coroutines/flow.html#asynchronous-flow) -Used to do asynchronous work without blocking the main thread and increasing productivity.
- [MVVM Architecture](https://developer.android.com/jetpack/guide) - The MVVM architecture used to cleanly separate the business and presentation logic of the application, using characteristics of MVVM such as unidirectional data flow, dependency flow, and loose coupling.
- [Android Jetpack](https://developer.android.com/jetpack) - Android Jetpack library is used to follow best practices, reduce boilerplate code, and write code that works consistently across Android versions and devices.
  - [Navigation](https://developer.android.com/jetpack/androidx/releases/navigation) - Navigation component is used to navigating between fragments
  - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - The ViewModel is used to store and manage UI-related data in a lifecycle conscious way.
  - [StateFlow-SharedFlow](https://developer.android.com/kotlin/flow/stateflow-and-sharedflow#stateflow) - StateFlow - SharedFlow is used to observe state owner flows that emits current and new status updates to their collectors.
  - [Room](https://developer.android.com/topic/libraries/architecture/room) - The Room library used to access the local SQLite database with object-relational mapper approach.
  - [DataStore](https://developer.android.com/topic/libraries/architecture/datastore) - DataStore is used with Flows to store data key-value pairs and typed objects.
  - [Hilt](https://developer.android.com/training/dependency-injection/hilt-android) - Hilt-Dagger library used to use independently of dependencies.
- [OkHttp](https://github.com/square/okhttp) - Used to HTTP client for making network calls.
- [Retrofit](https://github.com/square/retrofit) - Used to REST API clients.
- [Glide](https://github.com/bumptech/glide) - Used to image loading library using HTTP.
- [ConstraintLayout](https://developer.android.com/develop/ui/views/layout/constraint-layout) - ConstraintLayout used to create large and complex layouts without nested viewgroups.
- [GSON](https://github.com/google/gson) - JSON Parser, used to parse requests on the data layer for Entities and understands Kotlin non-nullable and default parameters.
- [ViewPager2](https://developer.android.com/jetpack/androidx/releases/viewpager2) - Used to slide between Fragments within adapters

For more information about used dependencies, see [this](https://github.com/RavanSA/ShoppingApp/blob/refactor/app/build.gradle) file.

## Architecture

![architecture](https://github.com/RavanSA/ShoppingApp/blob/master/preview_screen/mvvm.png)


