package com.brbx.design_system.components.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalInspectionMode
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun safeStringResource(res: StringResource): String =
    if (LocalInspectionMode.current) {
        res.key.split("_")
            .joinToString(" ") { it.replaceFirstChar { char -> char.uppercaseChar() } }
    } else {
        stringResource(res)
    }