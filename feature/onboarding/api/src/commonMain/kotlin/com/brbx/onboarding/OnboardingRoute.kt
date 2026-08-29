package com.brbx.onboarding

import com.brbx.navigation.EchoFlowNavKey
import kotlinx.serialization.Serializable

@Serializable
data class OnboardingRoute(
    val deeplink: String? = null,
) : EchoFlowNavKey