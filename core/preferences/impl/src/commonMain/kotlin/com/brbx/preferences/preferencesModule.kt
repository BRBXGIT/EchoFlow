package com.brbx.preferences

import com.brbx.preferences.datastore.datastoreModule
import com.brbx.preferences.manager.managerModule
import org.koin.dsl.module

val preferencesModule = module {
    includes(
        datastoreModule,
        managerModule,
    )
}