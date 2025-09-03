package me.partypronl.recur.domain.util

import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.daysUntil

object DateUtil {

    fun Instant.daysSinceEpoch(): Int {
        val epochDate = Instant.fromEpochMilliseconds(0L)
        return epochDate.daysUntil(this, TimeZone.UTC)
    }
}
