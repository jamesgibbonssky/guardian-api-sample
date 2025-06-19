# guardian-api-sample
An example application showcasing best practice Android app architecture using MVI and Clean Architecture principles.

## Implementation notes
The app is an example to be used as a starting point for a larger app. As such it has been architected with that growth in mind.
A clean architecture has been put in place with the core logic and model in the domain layer.
The network interactions in the data layer and the UI in the presentation layer.
Each layer has been isolated into its own module ensuring that internal details are hidden from other modules.
The dependency injection library Hilt has been used to construct the dependency graph.
Following modern Android best practice the UI has been implemented with Jetpack Compose with navigation between screens managed by the Jetpack Navigation library.
Communication between the ViewModels and the UI follows the Model View Intent (MVI) pattern (aka UDF - Unidirectional Data Flow) ensuring the interface between ViewModel and the composable screens is clean and easy to follow.
All classes that contain logic have unit test coverage using JUnit tests and use the mocking framework Mockk. Turbine is used to test the flows in the ViewModels.

Other libraries of note that have been used:
* OkHttp - for the Http connection and caching
* Retrofit - For the network interface
* Moshi - for deserialization from JSON to Kotlin objects in the network layer
* Timber - for logging
