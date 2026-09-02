package com.brbx.data.builder

internal actual fun AuthLinkBuilderImpl.platformState(state: String): String {
    val desktop = "desktop"
    val withDesktop = state.dropLast(n = desktop.length) + desktop
    return withDesktop
}