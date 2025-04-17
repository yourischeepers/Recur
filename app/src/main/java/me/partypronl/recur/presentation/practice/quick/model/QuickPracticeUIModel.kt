package me.partypronl.recur.presentation.practice.quick.model

import androidx.compose.runtime.Immutable
import me.partypronl.recur.domain.decks.model.CardsToPractice

@Immutable
data class QuickPracticeUIModel(
    val cardsToPractice: CardsToPractice,
    val isRepeating: Boolean,
)
