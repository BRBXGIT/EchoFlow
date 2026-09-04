package com.brbx.home

import com.brbx.navigation.TopLevelNavKey
import dev.chiksmedina.solar.BoldSolar
import dev.chiksmedina.solar.BrokenSolar
import dev.chiksmedina.solar.bold.EssentionalUi
import dev.chiksmedina.solar.bold.essentionalui.Home
import dev.chiksmedina.solar.broken.EssentionalUi
import dev.chiksmedina.solar.broken.essentionalui.Home
import echoflow.feature.home.api.generated.resources.Res
import echoflow.feature.home.api.generated.resources.home_top_level_key_label
import kotlinx.serialization.Serializable

@Serializable
object HomeRoute : TopLevelNavKey {
    override val textRes = Res.string.home_top_level_key_label
    override val selectedIcon = BoldSolar.EssentionalUi.Home
    override val unselectedIcon = BrokenSolar.EssentionalUi.Home
}