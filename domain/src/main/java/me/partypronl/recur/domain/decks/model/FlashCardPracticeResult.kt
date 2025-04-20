package me.partypronl.recur.domain.decks.model

sealed class FlashCardPracticeResult(val levelsDelta: Int) {

    data object Wrong : FlashCardPracticeResult(-4)
    data object Hard : FlashCardPracticeResult(0)
    data object Correct : FlashCardPracticeResult(1)
    data object Easy : FlashCardPracticeResult(2)
}
