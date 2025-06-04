package me.partypronl.recur.data.local.database

import kotlinx.datetime.Instant
import org.koin.core.annotation.Factory

@Factory
class InstantEntityMapper {

    fun mapToModel(instant: Long) = Instant.fromEpochMilliseconds(instant)

    fun mapToEntity(instant: Instant) = instant.toEpochMilliseconds()
}

