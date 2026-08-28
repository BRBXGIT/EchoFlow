package com.brbx.data.di

import com.brbx.data.builder.AuthLinkBuilder
import com.brbx.data.builder.AuthLinkBuilderImpl
import com.brbx.data.builder.PkceGenerator
import com.brbx.data.builder.PkceGeneratorImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal val authLinkBuilderModule = module {
    singleOf(constructor = ::PkceGeneratorImpl) { bind<PkceGenerator>() }

    singleOf(constructor = ::AuthLinkBuilderImpl) { bind<AuthLinkBuilder>() }
}