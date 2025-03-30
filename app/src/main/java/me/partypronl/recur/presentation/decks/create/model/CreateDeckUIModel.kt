package me.partypronl.recur.presentation.decks.create.model

import androidx.compose.runtime.Immutable

@Immutable
data class CreateDeckUIModel(
    val nameInput: String = "",
    val canCreateDeck: Boolean = false,
    val isCreating: Boolean = false,
)
