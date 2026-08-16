package com.brbx.data

import com.brbx.data.repository.repositoryModule
import org.koin.dsl.module

val dataModule = module {
    includes(repositoryModule)
}