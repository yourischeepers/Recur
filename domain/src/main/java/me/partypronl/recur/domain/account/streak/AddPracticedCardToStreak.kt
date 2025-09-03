package me.partypronl.recur.domain.account.streak

import kotlinx.coroutines.flow.first
import me.partypronl.recur.domain.account.streak.data.StreakRepository
import org.koin.core.annotation.Factory

@Factory
class AddPracticedCardToStreak(
    private val repository: StreakRepository,
    private val extendStreakIfRequirementsMet: ExtendStreakIfRequirementsMet,
) {

    suspend operator fun invoke() {
        val currentStreak = repository.observeStreak().first()
        val newStreak = currentStreak.copy(practicedToday = currentStreak.practicedToday + 1)
        repository.updateStreak(newStreak)
        extendStreakIfRequirementsMet()
    }
}
