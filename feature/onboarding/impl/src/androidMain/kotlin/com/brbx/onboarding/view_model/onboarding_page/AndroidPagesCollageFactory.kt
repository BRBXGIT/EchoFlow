package com.brbx.onboarding.view_model.onboarding_page

import com.brbx.onboarding.view_model.onboarding_page.OnboardingPage.IconCollage
import dev.chiksmedina.solar.BoldSolar
import dev.chiksmedina.solar.BrokenSolar
import dev.chiksmedina.solar.bold.ElectronicDevices
import dev.chiksmedina.solar.bold.EssentionalUi
import dev.chiksmedina.solar.bold.electronicdevices.SmartphoneVibration
import dev.chiksmedina.solar.bold.essentionalui.BatteryFull
import dev.chiksmedina.solar.broken.EssentionalUi
import dev.chiksmedina.solar.broken.Notifications
import dev.chiksmedina.solar.broken.Security
import dev.chiksmedina.solar.broken.SettingsFineTuning
import dev.chiksmedina.solar.broken.Weather
import dev.chiksmedina.solar.broken.essentionalui.BatteryCharge
import dev.chiksmedina.solar.broken.essentionalui.CheckCircle
import dev.chiksmedina.solar.broken.notifications.Bell
import dev.chiksmedina.solar.broken.notifications.BellBing
import dev.chiksmedina.solar.broken.notifications.NotificationUnread
import dev.chiksmedina.solar.broken.security.ShieldCheck
import dev.chiksmedina.solar.broken.settingsfinetuning.Settings
import dev.chiksmedina.solar.broken.weather.CloudBolt

internal object AndroidPagesCollageFactory {
    val notifications = IconCollage(
        topStart = BrokenSolar.Notifications.Bell,
        topEnd = BrokenSolar.Notifications.BellBing,
        center = BoldSolar.ElectronicDevices.SmartphoneVibration,
        bottomStart = BrokenSolar.Notifications.NotificationUnread,
        bottomEnd = BrokenSolar.EssentionalUi.CheckCircle,
    )

    val batteryOptimization = IconCollage(
        topStart = BrokenSolar.EssentionalUi.BatteryCharge,
        topEnd = BrokenSolar.SettingsFineTuning.Settings,
        center = BoldSolar.EssentionalUi.BatteryFull,
        bottomStart = BrokenSolar.Weather.CloudBolt,
        bottomEnd = BrokenSolar.Security.ShieldCheck,
    )
}