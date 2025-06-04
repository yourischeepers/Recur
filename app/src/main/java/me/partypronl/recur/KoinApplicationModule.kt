package me.partypronl.recur

import me.partypronl.recur.data.core.DataCoreModule
import me.partypronl.recur.data.local.DataLocalModule
import me.partypronl.recur.domain.DomainModule
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module

@Module(
    includes = [
        DomainModule::class,
        DataCoreModule::class,
        DataLocalModule::class,
    ]
)
@ComponentScan("me.partypronl.recur")
class KoinApplicationModule
