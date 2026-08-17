package com.brbx.onboarding.view_model.model

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.vector.ImageVector
import dev.chiksmedina.solar.BoldSolar
import dev.chiksmedina.solar.BrokenSolar
import dev.chiksmedina.solar.bold.ElectronicDevices
import dev.chiksmedina.solar.bold.EssentionalUi
import dev.chiksmedina.solar.bold.Hands
import dev.chiksmedina.solar.bold.Users
import dev.chiksmedina.solar.bold.electronicdevices.SmartphoneVibration
import dev.chiksmedina.solar.bold.essentionalui.BatteryFull
import dev.chiksmedina.solar.bold.hands.HandShake
import dev.chiksmedina.solar.bold.users.UserCircle
import dev.chiksmedina.solar.broken.Astronomy
import dev.chiksmedina.solar.broken.ElectronicDevices
import dev.chiksmedina.solar.broken.EssentionalUi
import dev.chiksmedina.solar.broken.Like
import dev.chiksmedina.solar.broken.Notifications
import dev.chiksmedina.solar.broken.Security
import dev.chiksmedina.solar.broken.SettingsFineTuning
import dev.chiksmedina.solar.broken.Weather
import dev.chiksmedina.solar.broken.astronomy.StarFall
import dev.chiksmedina.solar.broken.astronomy.StarsMinimalistic
import dev.chiksmedina.solar.broken.electronicdevices.AirbudsCaseOpen
import dev.chiksmedina.solar.broken.essentionalui.BatteryCharge
import dev.chiksmedina.solar.broken.essentionalui.CheckCircle
import dev.chiksmedina.solar.broken.like.Heart
import dev.chiksmedina.solar.broken.notifications.Bell
import dev.chiksmedina.solar.broken.notifications.BellBing
import dev.chiksmedina.solar.broken.notifications.NotificationUnread
import dev.chiksmedina.solar.broken.security.EyeClosed
import dev.chiksmedina.solar.broken.security.Key
import dev.chiksmedina.solar.broken.security.Lock
import dev.chiksmedina.solar.broken.security.ShieldCheck
import dev.chiksmedina.solar.broken.settingsfinetuning.Settings
import dev.chiksmedina.solar.broken.weather.CloudBolt
import echoflow.feature.onboarding.impl.generated.resources.Res
import echoflow.feature.onboarding.impl.generated.resources.description_authentication
import echoflow.feature.onboarding.impl.generated.resources.description_battery_optimization
import echoflow.feature.onboarding.impl.generated.resources.description_greeting
import echoflow.feature.onboarding.impl.generated.resources.description_notifications
import echoflow.feature.onboarding.impl.generated.resources.label_action_button
import echoflow.feature.onboarding.impl.generated.resources.label_authnticate_button
import echoflow.feature.onboarding.impl.generated.resources.title_authentication
import echoflow.feature.onboarding.impl.generated.resources.title_battery_optimization
import echoflow.feature.onboarding.impl.generated.resources.title_greeting
import echoflow.feature.onboarding.impl.generated.resources.title_notifications
import org.jetbrains.compose.resources.StringResource

@Immutable
internal data class OnboardingState(
    val currentPageIndex: Int = 0,
    val currentPage: Int = currentPageIndex + 1,
    val pages: List<OnboardingPage> = createPages(),
)

internal expect fun createPages(): List<OnboardingPage>

internal sealed interface OnboardingPage {
    val title: StringResource
    val description: StringResource
    val collage: IconCollage
    val action: Action?

    data class Greeting(
        override val title: StringResource = Res.string.title_greeting,
        override val description: StringResource = Res.string.description_greeting,
        override val action: Action? = null,
        override val collage: IconCollage = IconCollage(
            topStart = BrokenSolar.ElectronicDevices.AirbudsCaseOpen,
            topEnd = BrokenSolar.Astronomy.StarFall,
            center = BoldSolar.Hands.HandShake,
            bottomStart = BrokenSolar.Like.Heart,
            bottomEnd = BrokenSolar.Astronomy.StarsMinimalistic,
        ),
    ) : OnboardingPage

    data class Notifications(
        override val title: StringResource = Res.string.title_notifications,
        override val description: StringResource = Res.string.description_notifications,
        override val action: Action = Action(),
        override val collage: IconCollage = IconCollage(
            topStart = BrokenSolar.Notifications.Bell,
            topEnd = BrokenSolar.Notifications.BellBing,
            center = BoldSolar.ElectronicDevices.SmartphoneVibration,
            bottomStart = BrokenSolar.Notifications.NotificationUnread,
            bottomEnd = BrokenSolar.EssentionalUi.CheckCircle,
        ),
    ) : OnboardingPage

    data class BatteryOptimization(
        override val title: StringResource = Res.string.title_battery_optimization,
        override val description: StringResource = Res.string.description_battery_optimization,
        override val action: Action = Action(canSkip = true),
        override val collage: IconCollage = IconCollage(
            topStart = BrokenSolar.EssentionalUi.BatteryCharge,
            topEnd = BrokenSolar.SettingsFineTuning.Settings,
            center = BoldSolar.EssentionalUi.BatteryFull,
            bottomStart = BrokenSolar.Weather.CloudBolt,
            bottomEnd = BrokenSolar.Security.ShieldCheck,
        ),
    ) : OnboardingPage

    data class Authentication(
        override val title: StringResource = Res.string.title_authentication,
        override val description: StringResource = Res.string.description_authentication,
        override val action: Action = Action(text = Res.string.label_authnticate_button),
        override val collage: IconCollage = IconCollage(
            topStart = BrokenSolar.Security.Lock,
            topEnd = BrokenSolar.Security.Key,
            center = BoldSolar.Users.UserCircle,
            bottomStart = BrokenSolar.Security.EyeClosed,
            bottomEnd = BrokenSolar.Security.ShieldCheck,
        ),
    ) : OnboardingPage

    data class IconCollage(
        val topStart: ImageVector,
        val topEnd: ImageVector,
        val center: ImageVector,
        val bottomStart: ImageVector,
        val bottomEnd: ImageVector,
    )

    data class Action(
        val text: StringResource = Res.string.label_action_button,
        val canSkip: Boolean = false,
    )
}