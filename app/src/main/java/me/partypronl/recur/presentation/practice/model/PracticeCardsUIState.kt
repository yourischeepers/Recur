package me.partypronl.recur.presentation.practice.model

sealed interface PracticeCardsUIState {

    data class Practicing(val uiModel: PracticeCardsUIModel) : PracticeCardsUIState
    data object Finished : PracticeCardsUIState
}
