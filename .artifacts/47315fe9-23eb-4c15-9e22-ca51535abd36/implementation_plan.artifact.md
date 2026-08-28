# Light Refactoring of EchoFlow Project

This plan outlines a series of light refactoring tasks across the project to improve code consistency, naming, and follow modern Kotlin idioms.

## Proposed Changes

### [Naming and Style Improvements]

- **Convert functions to expression bodies (`=`)**: Where the function body consists of a single expression (or can be easily transformed into one), use the expression body syntax.
- **Explicit return types**: Ensure all functions that return a value have an explicit return type (except for `Unit`).
- **General Naming**: Review and improve naming for better clarity.

### [Module: feature/onboarding/impl]

#### [MODIFY] [AndroidPagesDelegate.kt](file:///C:/PRG/KotlinProjects/EchoFlow/feature/onboarding/impl/src/androidMain/kotlin/com/brbx/onboarding/view_model/AndroidPagesDelegate.kt)
- Convert `invoke` to expression body.
- Convert `checkIsGranted` to expression body.

#### [MODIFY] [OnboardingViewModel.kt](file:///C:/PRG/KotlinProjects/EchoFlow/feature/onboarding/impl/src/commonMain/kotlin/com/brbx/onboarding/view_model/OnboardingViewModel.kt)
- Convert `dispatchIntent` to expression body.

#### [MODIFY] [AuthDelegate.kt](file:///C:/PRG/KotlinProjects/EchoFlow/feature/onboarding/impl/src/commonMain/kotlin/com/brbx/onboarding/view_model/AuthDelegate.kt)
- Review and refactor functions to expression bodies.

### [Module: feature/navigation/impl]

#### [MODIFY] [NavigatorImpl.kt](file:///C:/PRG/KotlinProjects/EchoFlow/feature/navigation/impl/src/commonMain/kotlin/com/brbx/navigation/NavigatorImpl.kt)
- Convert `navigate`, `navigateBack`, and `removePrevious` to expression bodies.

### [Module: core/data/impl]

#### [MODIFY] [AuthLinkBuilderImpl.kt](file:///C:/PRG/KotlinProjects/EchoFlow/core/data/impl/src/commonMain/kotlin/com/brbx/data/builder/AuthLinkBuilderImpl.kt)
- Convert `getLink` and `generateLink` (using `run`) to expression bodies.

#### [MODIFY] [PkceGenerator.kt](file:///C:/PRG/KotlinProjects/EchoFlow/core/data/impl/src/commonMain/kotlin/com/brbx/data/builder/PkceGenerator.kt)
- Convert `generateRandomString` and `generateCodeChallenge` to expression bodies.

### [Module: core/preferences/impl]

#### [MODIFY] [AuthPrefsManagerImpl.kt](file:///C:/PRG/KotlinProjects/EchoFlow/core/preferences/impl/src/commonMain/kotlin/com/brbx/preferences/manager/AuthPrefsManagerImpl.kt)
- Review and refactor functions.

## Verification Plan

### Automated Tests
- Run existing unit tests (if any) to ensure no regressions.
- `gradlew assembleDebug` to verify compilation.

### Manual Verification
- Code review of the changes.
