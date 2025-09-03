package me.partypronl.recur.domain.account.streak.data

import kotlinx.coroutines.flow.Flow
import me.partypronl.recur.domain.account.streak.model.Streak

interface StreakRepository {

    fun observeStreak(): Flow<Streak>
    suspend fun updateStreak(streak: Streak)
}
