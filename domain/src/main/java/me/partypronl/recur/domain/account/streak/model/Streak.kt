package me.partypronl.recur.domain.account.streak.model

import kotlinx.serialization.Serializable
import kotlin.math.max

@Serializable
data class Streak(
    val length: Int,
    val practicedToday: Int,
    val lastExtensionDay: Int?,
) {

    val cardsLeftToday = max(MinimumCardsToExtendStreak - practicedToday, 0)

    companion object {

        const val MinimumCardsToExtendStreak = 10
    }
}
