# Kotlin Multiplatform (KMP) Architecture

This project leverages **Kotlin Multiplatform (KMP)** and **Compose Multiplatform** to share code
across Android and iOS, maximizing efficiency while maintaining high performance and a consistent
user experience.

## Core Architecture Principles

The project follows **Clean Architecture** combined with the **MVVM (Model-View-ViewModel)**
pattern. This separation of concerns ensures the codebase is testable, maintainable, and easy to
extend.

### 1. Shared UI (Compose Multiplatform)

Unlike traditional KMP where only logic is shared, this project uses **Compose Multiplatform**. This
allows us to write the UI once in Kotlin and run it on both Android and iOS.

- **Location**: `composeApp/src/commonMain/kotlin/.../presentation`
- **Potential**: Near 100% UI code reuse while still accessing platform-specific APIs when needed.

### 2. Domain Layer (Pure Kotlin)

Contains the core business rules of the application. It is completely independent of any
platform-specific libraries or the UI.

- **Models**: Simple data classes representing the data.
- **Use Cases**: Encapsulate specific business logic (e.g., `InitiateTransactionUseCase`).
- **Location**: `composeApp/src/commonMain/kotlin/.../domain`

### 3. Data Layer (Infrastructure)

Handles data retrieval from remote sources.

- **Repositories**: Abstract definitions of data operations.
- **Remote Sources**: Implementation of networking using **Ktor**.
- **Location**: `composeApp/src/commonMain/kotlin/.../data`

### 4. Platform-Specific Code (Expect/Actual)

In this project, we demonstrate the power of `expect`/`actual` through our **Firebase Firestore**
implementation for Android.

While the interface for our data repository is defined in the shared `commonMain` module, we use the
`expect` keyword to indicate that the implementation will be platform-specific. On the Android
side (`androidMain`), we provide the `actual` implementation using the native **Firebase Android SDK
**.

This approach allows us to:

- Use native, optimized SDKs provided by Google for Android.
- Keep the domain and presentation layers completely unaware of the platform-specific details.
- Easily swap or add different implementations for other platforms (like iOS) in the future without
  touching the shared business logic.

## Cross-Platform Potential

- **Code Reuse**: Shared logic typically covers 70-90% of the codebase, including networking,
  serialization, and business rules. With Compose Multiplatform, this reaches nearly 100% for the UI
  as well.
- **Consistency**: Business logic is written and tested once, reducing the risk of discrepancies
  between Android and iOS apps.
- **Faster Time-to-Market**: Features are developed simultaneously for both platforms.
- **Native Performance**: Kotlin compiles to JVM bytecode for Android and to native binaries for
  iOS, ensuring no performance overhead.
- **Wider Platform Reach**: KMP's flexibility allows targeting more than just mobile. For an example
  of a single codebase deployable to Android, iOS, and Desktop,
  see [Unsplash-KMP](https://github.com/dalafiarisamuel/Unsplash-KMP) (written by me).

## Summary of the Stack

- **Logic & UI**: Kotlin, Compose Multiplatform
- **Networking**: Ktor
- **DI**: Koin
- **Async**: Coroutines & Flow
- **Testing**: Kotest & Mokkery
