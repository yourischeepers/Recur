package me.partypronl.recur.domain.account.streak

import kotlinx.coroutines.flow.Flow
import me.partypronl.recur.domain.account.streak.data.StreakRepository
import me.partypronl.recur.domain.account.streak.model.Streak
import org.koin.core.annotation.Factory

@Factory
class ObserveStreak(
    private val repository: StreakRepository,
) {

    operator fun invoke(): Flow<Streak> {
        return repository.observeStreak()
    }
}
