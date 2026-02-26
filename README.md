# Cashi Payment - Kotlin Multiplatform (KMP)

Cashi Payment is a mobile application built using **Kotlin Multiplatform (KMP)** and **Compose Multiplatform**, targeting both **Android** and **iOS**. The project demonstrates a modern approach to cross-platform development with a shared codebase for business logic, data, and UI.

## Getting Started

### Prerequisites
- **Android Studio** (Latest Ladybug or newer)
- **Xcode** (For iOS development)
- **Kotlin Multiplatform Plugin** installed in Android Studio

### Project Structure
- `composeApp/src/commonMain`: Shared business logic, domain models, use cases, and Compose UI.
- `composeApp/src/androidMain`: Android-specific implementations and resources.
- `composeApp/src/iosMain`: iOS-specific implementations.
- `iosApp`: Swift-based entry point for the iOS application.

---

## Architecture

The project follows **Clean Architecture** principles combined with **MVVM** for the presentation layer:

1. **Presentation Layer**:
    - **ViewModels**: Manage UI state using `StateFlow` and handle user interactions.
    - **Compose Multiplatform**: Declarative UI shared between Android and iOS.
2. **Domain Layer**:
    - **Models**: Pure Kotlin data classes.
    - **Use Cases**: Encapsulate specific business logic (e.g., `InitiateTransactionUseCase`, `GetAllPaymentUseCase`).
3. **Data Layer**:
    - **Repositories**: Abstract data sources.
    - **Remote Sources**: Handle API calls (Ktor).

### Key Libraries
- **Compose Multiplatform**: Shared UI.
- **Koin**: Dependency Injection.
- **Ktor**: Networking.
- **Kotlinx Serialization**: JSON parsing.
- **Kotlinx Coroutines**: Asynchronous programming.
- **Napier**: Logging.

---

## Testing

The project emphasizes quality through unit testing of ViewModels and Use Cases.

### Test Stack
- **Kotest**: Powerful testing framework using `BehaviorSpec` for descriptive tests.
- **Mokkery**: Kotlin Multiplatform mocking library.
- **Turbine**: Small library for testing Kotlin Coroutines `Flow`.

> **Note:** To run Kotest tests directly from Android Studio, ensure you have the [Kotest plugin](https://plugins.jetbrains.com/plugin/13308-kotest) installed.

### How to run tests
You can run all tests from the terminal:

- **Common Tests**:
  ```shell
  ./gradlew :composeApp:allTests
  ```

- **Android Specific Tests**:
  ```shell
  ./gradlew :composeApp:testDebugUnitTest
  ```

---

## How to Run the App

### Android
1. Open the project in Android Studio.
2. Select `composeApp` in the run configurations.
3. Click the **Run** button or use the terminal:
   ```shell
   ./gradlew :composeApp:installDebug
   ```

### iOS
1. Open the `iosApp` folder in Xcode.
2. Select a simulator or physical device.
3. Click the **Run** button.
   *Note: You can also run the iOS app directly from Android Studio if you have the KMP plugin and Xcode installed.*

---

Learn more about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html).
