package me.partypronl.recur.presentation.decks.edit.card.create.model

import androidx.compose.runtime.Immutable

@Immutable
data class CreateCardUIModel(
    val frontInput: String = "",
    val backInput: String = "",
    val canCreateCard: Boolean = false,
    val isCreating: Boolean = false,
)
