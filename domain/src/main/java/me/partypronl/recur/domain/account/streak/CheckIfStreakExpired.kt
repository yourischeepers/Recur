package me.partypronl.recur.domain.account.streak

import kotlinx.coroutines.flow.first
import kotlinx.datetime.Clock
import me.partypronl.recur.domain.account.streak.data.StreakRepository
import me.partypronl.recur.domain.util.DateUtil.daysSinceEpoch
import org.koin.core.annotation.Factory

@Factory
class CheckIfStreakExpired(
    private val repository: StreakRepository,
) {

    suspend operator fun invoke() {
        val currentStreak = repository.observeStreak().first()
        val today = Clock.System.now().daysSinceEpoch()

        if (currentStreak.lastExtensionDay == null) return
        if (currentStreak.lastExtensionDay >= today - 1) return

        val newStreak = currentStreak.copy(
            length = 0,
            lastExtensionDay = null,
        )
        repository.updateStreak(newStreak)
    }
}
