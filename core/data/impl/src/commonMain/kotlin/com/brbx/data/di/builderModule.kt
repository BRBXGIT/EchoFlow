package com.brbx.data.di

import com.brbx.data.builder.AuthUrlBuilder
import com.brbx.data.builder.AuthUrlBuilderImpl
import com.brbx.data.builder.PkceGenerator
import com.brbx.data.builder.PkceGeneratorImpl
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

internal val builderModule = module {
    factoryOf(::PkceGeneratorImpl) bind PkceGenerator::class
    singleOf(::AuthUrlBuilderImpl) bind AuthUrlBuilder::class
}
