package com.brbx.onboarding.utils

import com.brbx.onboarding.model.OnboardingPage
import dev.chiksmedina.solar.BoldSolar
import dev.chiksmedina.solar.BrokenSolar
import dev.chiksmedina.solar.bold.Hands
import dev.chiksmedina.solar.bold.Users
import dev.chiksmedina.solar.bold.hands.HandShake
import dev.chiksmedina.solar.bold.users.UserCircle
import dev.chiksmedina.solar.broken.Astronomy
import dev.chiksmedina.solar.broken.ElectronicDevices
import dev.chiksmedina.solar.broken.Like
import dev.chiksmedina.solar.broken.Security
import dev.chiksmedina.solar.broken.astronomy.StarFall
import dev.chiksmedina.solar.broken.astronomy.StarsMinimalistic
import dev.chiksmedina.solar.broken.electronicdevices.AirbudsCaseOpen
import dev.chiksmedina.solar.broken.like.Heart
import dev.chiksmedina.solar.broken.security.EyeClosed
import dev.chiksmedina.solar.broken.security.Key
import dev.chiksmedina.solar.broken.security.Lock
import dev.chiksmedina.solar.broken.security.ShieldCheck

internal object CommonPagesCollageFactory {

    val greeting = OnboardingPage.IconCollage(
        topStart = BrokenSolar.ElectronicDevices.AirbudsCaseOpen,
        topEnd = BrokenSolar.Astronomy.StarFall,
        center = BoldSolar.Hands.HandShake,
        bottomStart = BrokenSolar.Like.Heart,
        bottomEnd = BrokenSolar.Astronomy.StarsMinimalistic,
    )

    val authentication = OnboardingPage.IconCollage(
        topStart = BrokenSolar.Security.Lock,
        topEnd = BrokenSolar.Security.Key,
        center = BoldSolar.Users.UserCircle,
        bottomStart = BrokenSolar.Security.EyeClosed,
        bottomEnd = BrokenSolar.Security.ShieldCheck,
    )
}