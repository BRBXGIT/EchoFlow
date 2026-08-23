package com.brbx.onboarding

import com.brbx.navigation.EchoFlowNavKey
import com.brbx.navigation.Navigator
import kotlinx.serialization.Serializable

@Serializable
object OnboardingRoute : EchoFlowNavKey

fun Navigator.onboarding() {
    navigate(key = OnboardingRoute)
}