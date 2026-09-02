package com.brbx.onboarding.model

import androidx.compose.runtime.Immutable

@Immutable
internal interface DesktopPagePayload

internal data object DesktopGreetingPayload : DesktopPagePayload

internal data object DesktopAuthPayload : DesktopPagePayload
