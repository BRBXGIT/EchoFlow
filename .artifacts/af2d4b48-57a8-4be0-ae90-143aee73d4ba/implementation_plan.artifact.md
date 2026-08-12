# Implementation Plan - Fix TOML Library Issues

Fix build errors and library resolution issues by updating `libs.versions.toml`.

## Proposed Changes

### Build Configuration

#### [MODIFY] [libs.versions.toml](file:///C:/PRG/KotlinProjects/EchoFlow/gradle/libs.versions.toml)

- **Fix AGP Version**: Downgrade `android-gradle-plugin` from `9.7.0` to `9.3.1` (latest stable).
- **Fix Lifecycle Groups**: Change group for Lifecycle libraries from `androidx.lifecycle` to `org.jetbrains.androidx.lifecycle` to match their multiplatform counterparts mentioned in the `# Jetbrains` section.
- **Fix KSP Version**: Update `ksp` from `2.3.9` to `2.3.11` to match available stable version.
- **Harmonize Keys for jvm-app**: Update library/plugin keys to match usage in `jvm-app/build.gradle.kts` while maintaining compatibility with other modules:
    - `kotlin-jvm` -> `kotlinJvm`
    - `compose-multiplatform` -> `composeMultiplatform`
    - `compose-compiler` -> `composeCompiler`
    - `kotlinx-coroutines-swing` -> `kotlinx-coroutinesSwing`
    - `compose-preview` -> `compose-uiToolingPreview`

> [!WARNING]
> Renaming keys in TOML might require updates in other `build.gradle.kts` files (like root) if they use the old kebab-case names. However, since the request is "ONLY with toml", I will prioritize matching the usage in the sub-projects which seem to be the primary targets of these definitions.

## Verification Plan

### Automated Tests
- Run `./gradlew help` to verify build script compilation.
