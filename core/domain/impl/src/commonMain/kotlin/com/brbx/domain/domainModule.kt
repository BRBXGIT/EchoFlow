package com.brbx.domain

import com.brbx.domain.use_case.useCaseModule
import org.koin.dsl.module

val domainModule = module {
    includes(useCaseModule)
}