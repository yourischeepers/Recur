package me.partypronl.recur.domain.account.streak

import kotlinx.coroutines.flow.first
import kotlinx.datetime.Clock
import me.partypronl.recur.domain.account.streak.data.StreakRepository
import me.partypronl.recur.domain.util.DateUtil.daysSinceEpoch
import org.koin.core.annotation.Factory

@Factory
class ExtendStreakIfRequirementsMet(
    private val repository: StreakRepository,
) {

    suspend operator fun invoke() {
        val currentStreak = repository.observeStreak().first()
        if (currentStreak.cardsLeftToday != 0) return

        val today = Clock.System.now().daysSinceEpoch()
        if (currentStreak.lastExtensionDay != null && today <= currentStreak.lastExtensionDay) return

        val newStreak = currentStreak.copy(
            length = currentStreak.length + 1,
            lastExtensionDay = today,
        )
        repository.updateStreak(newStreak)
    }
}
