# EchoFlow Agent Guide (`AGENTS.md`)

This guide serves as the definitive reference for AI coding agents and developers working on **EchoFlow**.
Always consult and adhere to these guidelines when designing, modifying, refactoring, or extending the codebase.

---

## 1. Project Overview

**EchoFlow** is an open-source, multiplatform client for SoundCloud built with **Kotlin Multiplatform (KMP)** and **Compose Multiplatform**.

- **Supported Platforms**: Android (SDK 28–37) & Desktop JVM (Java 21).
- **Core Capabilities**: SoundCloud API integration, dynamic adaptive UI, cross-platform audio playback engine, SoundCloud deeplink resolution, and modern MVI presentation layer.

---

## 2. Architecture & Modularization

The project is structured according to Clean Architecture and strict modularization principles with API/IMPL separation.

### 2.1 Module Hierarchy

```
EchoFlow/
├── android-app/                # Android application entry point & Manifest
├── jvm-app/                    # Desktop application entry point & JVM runner
├── common-app/                 # Top-level composition, DI graph wiring, AppViewModel
├── feature/
│   ├── common/                 # Base MVI delegates, shared UI models, pagination helpers
│   ├── navigation/             # Navigation contract (api) & graph implementation (impl)
│   ├── home/                   # Home screen (api & impl)
│   ├── playlist/               # Playlist & album screens (api & impl)
│   ├── player/                 # Mini & expanded player (api & impl)
│   └── onboarding/             # Authentication & onboarding flow (api & impl)
├── core/
│   ├── common/                 # Foundation utils, dispatchers, primitives
│   ├── data/                   # Repositories, controllers, data sources (api & impl)
│   ├── domain/                 # Domain models, use case definitions & implementations
│   ├── network/                # Ktor client, SoundCloud API endpoints, serialization
│   ├── preferences/            # DataStore persistent settings & tokens (api & impl)
│   ├── design-system/
│   │   ├── theme/              # Material 3 tokens, dynamic palettes, typography
│   │   └── components/         # Reusable UI components (buttons, cards, tracks)
│   ├── debug/                  # Previews, logging, debugging tools
│   └── local-server/           # Embedded Ktor CIO server for OAuth redirect on desktop
└── build-logic/
    └── convention/             # Custom Gradle convention plugins
```

### 2.2 API vs. IMPL Rules

1. **`*api` modules**:
   - Contain public interfaces, domain models, navigation keys, and use case interfaces.
   - **Zero implementation details**; lightweight and fast to compile.
   - Other modules depend on `*api` modules to avoid cyclic and bloated dependencies.
2. **`*impl` modules**:
   - Contain implementations, internal helpers, DI module definitions (`*Module.kt`), and MVI delegates.
   - Classes and functions inside `impl` modules should be marked `internal` whenever possible.
   - An `impl` module depends on its corresponding `api` module, plus required core/feature `api` modules.

---

## 3. Tech Stack & Dependencies

| Area | Library / Technology | Version / Notes |
|---|---|---|
| **Language & Toolchain** | Kotlin 2.4.x, JVM 21, AGP 9.2.x | Configured in `build-logic` & `libs.versions.toml` |
| **UI Framework** | Compose Multiplatform 1.12.x | Material 3 + Material 3 Adaptive |
| **Navigation** | AndroidX Navigation 3 | Type-safe keys via `kotlinx.serialization` |
| **Dependency Injection** | Koin 4.x (`koin-core`, `koin-compose`) | Constructor DSL (`singleOf`, `factoryOf`, `scopedMviDelegateOf`) |
| **MVI Framework** | `BrbxMvi` | Unidirectional Data Flow, Scoped ViewModels, Delegates |
| **Audio Engine** | ExoPlayer Media3 (Android) / JavaFX Media & VLC (JVM) | Unified behind `PlayerController` |
| **Networking** | Ktor 3.x (OkHttp engine on Android, CIO server on JVM) | Content negotiation, Auth plugins |
| **Serialization** | `kotlinx.serialization.json` | Version 1.11.x |
| **Image Loading** | Coil 3 Multiplatform | Version 3.6.x |
| **Theming & Design** | `materialKolor` 5.x + Solar Icons | Seed-based expressive theming |
| **Storage** | AndroidX DataStore Preferences | Multiplatform datastore |

---

## 4. Coding Style & Conventions

### 4.1 Expression-Body Functions (`=`) Rule

- **Prefer single-expression body (`=`) for clean, concise one-liners**:
  When a function consists of a single expression, return statement, delegation, or direct mapping, write it with `=`.

  ```kotlin
  // GOOD: Clean, concise single expressions
  override fun invoke() = playerController.skipToNext()

  fun isPlaying() = state.value.status == PlaybackStatus.Playing

  override fun dispatchIntent(intent: PlaylistIntent) =
      when (intent) {
          is PlaylistIntent.BindPlaylist -> playlistBinder(intent)
          is PlaylistIntent.LoadNext -> playlistLoader(intent)
          is PlaylistIntent.PlayPlaylist -> playlistDj(intent)
      }

  private fun mapTrack(dto: TrackDto) = Track(
      id = dto.id,
      title = dto.title,
      durationMs = dto.duration,
  )
  ```

- **NO Extremism / Artificial Wrappers**:
  Do **NOT** force multi-line procedural code, loops, or complex side-effects into single-expression syntax using artificial helpers like `run { ... }`, `let { ... }`, or chaining just to have `=`.

  ```kotlin
  // BAD: Do NOT do this!
  fun updateState() = run {
      validateInput()
      val result = calculate()
      notifyListeners(result)
  }

  // GOOD: Use a standard function block for multi-line procedural work
  fun updateState() {
      validateInput()
      val result = calculate()
      notifyListeners(result)
  }
  ```

### 4.2 Visibility & Encapsulation
- Keep declarations `internal` within `impl` modules unless they must be exposed to tests or cross-module consumers.
- Keep ViewModel properties, MVI delegates, and helper composables `private` or `internal`.
- Declare properties as `val` by default; avoid `var` unless strictly required for mutable state machines.

### 4.3 Naming Conventions
- **Use Cases**: Fun interfaces named `*UseCase` with `operator fun invoke(...)`. Implementation: `*UseCaseImpl`.
- **MVI Delegates**: Named after their specific role, e.g., `PlaylistBinder`, `PlaylistLoader`, `PlaylistDj`.
- **ViewModels**: Suffix with `ViewModel` (e.g. `PlaylistViewModel`, `PlayerViewModel`).
- **Navigation Keys**: `@Serializable data class <Screen>NavKey(...) : EchoFlowNavKey`.
- **Koin Modules**: Name files `*Module.kt` and top-level val `<feature>Module`.

### 4.4 Formatting
- 4-space indentation.
- Trailing commas for multi-line parameter lists, arguments, and collection literals.
- Group imports alphabetically; remove unused imports.

---

## 5. MVI & Presentation Layer

EchoFlow uses the **BrbxMvi** architecture combined with scoped decomposition.

### 5.1 Pattern Structure
1. **State (`*State`)**:
   - An immutable `data class` with default values.
   - Use `kotlinx.collections.immutable` (`ImmutableList`, `persistentListOf()`) for collection fields.
2. **Intent (`*Intent`)**:
   - A sealed interface representing user actions or external triggers.
3. **Screen Effect (`*Effect` / `ScreenEffect`)**:
   - One-shot UI events (navigation, snackbar, notifications).
4. **Scoped ViewModel (`ScopedEchoFlowViewModel`)**:
   - Inherits from `ScopedEchoFlowViewModel<State, Intent, ScreenEffect>`.
   - Delegates intents to focused delegates via `by injectDelegate<MyDelegate>()`.
   - Keeps `dispatchIntent` clean as a simple `when (intent)` expression.
5. **MVI Delegates (`EchoFlowIntentDelegate`)**:
   - Specific, modular handlers for slices of business logic.
   - Registered in Koin using `scopedMviDelegateOf(::MyDelegate)`.
   - Use `reduce { copy(...) }` to mutate state, `postEffect(...)` for effects, and `bindLatest(...)` / `launchAction { ... }` for async work.

---

## 6. Player Subsystem Architecture

The audio playback subsystem is one of the most critical parts of the application.

- **Contract**: `PlayerController` in `:core:data:api`. Exposes `playerState: StateFlow<PlayerState>` and transport controls (`play`, `pause`, `resume`, `seekTo`, `skipToNext`, `skipToPrevious`, `addToQueue`, `setQueue`).
- **Shared Base**: `BasePlayerController` in `:core:data:impl`. Holds common queue management and player state.
  - `addToQueue(tracks)`: Appends to the end of the queue (FIFO queue behavior).
  - `setQueue(tracks, targetTrack)`: Replaces queue and selects the target track.
  - Always guard empty queues against out-of-bounds indexing (`currentTrack` returns `null` if queue is empty or index is invalid).
- **Platform Implementations**:
  - `AndroidPlayerController`: Backed by AndroidX Media3 (`ExoPlayer`) and bound to `PlaybackService` (`MediaSessionService`). Injected as a singleton to maintain persistent background audio. Maps playback status correctly across buffering, idle, ready, and ended states.
  - `JvmPlayerController`: Backed by JavaFX MediaPlayer (with VLC fallback). Handles stream URL resolution and media event subscriptions.

---

## 7. UI & Design System

- **Tokens**: Always prefer EchoFlow tokens over hardcoded values:
  - `mColors`: `MaterialTheme.colorScheme` (dynamic palette based on seed).
  - `mDimens`: `LocalDimens.current` (paddings, corner radii, icon sizes).
  - `mMotion`: `LocalMotion.current` (animations, transitions, spring specs).
  - `mTypography`: `MaterialTheme.typography`.
  - `mShapes`: `LocalShapes.current`.
- **Adaptive Layouts**: Support both compact phone layouts and expanded desktop/tablet layouts. Use `rememberIsLargeScreen()` or Compose Adaptive Navigation suite.
- **Previews**:
  - `@EchoFlowPreview` for reusable component previews (provides light & dark wrappers).
  - `@EchoFlowScreenPreview` for full-screen previews.

---

## 8. Build, Test & Development Workflow

### 8.1 Common Gradle Commands

Run commands from the repository root:

- **Android Unit Tests**:
  ```powershell
  ./gradlew testDebugUnitTest
  ```
- **Android Assembly**:
  ```powershell
  ./gradlew assembleDebug
  ```
- **Desktop Application Run**:
  ```powershell
  ./gradlew :jvm-app:run
  ```
- **Desktop Tests**:
  ```powershell
  ./gradlew :jvm-app:test
  ```
- **Full Verification**:
  ```powershell
  ./gradlew testDebugUnitTest assembleDebug
  ```

### 8.2 Working with Dependencies & Plugins
- Dependency versions are maintained in [gradle/libs.versions.toml](file:///C:/PRG/KotlinProjects/EchoFlow/gradle/libs.versions.toml).
- New modules should apply the appropriate convention plugin from `build-logic`:
  - `echoflow.kmp.library` for shared multiplatform libraries.
  - `echoflow.compose.multiplatform` for Compose-enabled multiplatform modules.
  - `echoflow.android.app` for the Android application target.
  - `echoflow.jvm.app` for the Desktop JVM application target.
