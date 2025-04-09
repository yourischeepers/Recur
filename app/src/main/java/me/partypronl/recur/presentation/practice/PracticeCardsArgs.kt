package me.partypronl.recur.presentation.practice

import me.partypronl.recur.domain.decks.model.CardToPractice

data class PracticeCardsArgs(
    val cardsToPractice: List<CardToPractice>,
    val trackResult: Boolean,
)
