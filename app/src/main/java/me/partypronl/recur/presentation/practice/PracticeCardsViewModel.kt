package me.partypronl.recur.presentation.practice

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import me.partypronl.recur.domain.decks.model.CardToPractice
import me.partypronl.recur.domain.decks.model.FlashCardPracticeResult
import org.koin.android.annotation.KoinViewModel
import org.koin.core.annotation.InjectedParam

@KoinViewModel
class PracticeCardsViewModel(
    @InjectedParam private val args: PracticeCardsArgs,
    private val mapper: PracticeCardsUIMapper,
) : ViewModel() {

    private val cardQueue = args.cardsToPractice.toMutableList()
    private var currentCard = pickFirstCardFromQueue()
    private var showBack = false

    private val _uiModel = MutableStateFlow(currentCard?.let { mapper.toUIModel(it, showBack) })
    val uiModel = _uiModel.asStateFlow()

    private fun pickFirstCardFromQueue(): CardToPractice? {
        val first = cardQueue.firstOrNull() ?: return null
        cardQueue.removeAt(0)
        return first
    }

    fun onRevealBackClicked() {
        currentCard ?: return
        showBack = true
        updateUIModel()
    }

    fun onResultButtonClicked(result: FlashCardPracticeResult) {
        if (!showBack) return

        // TODO call use case to update data

        currentCard = pickFirstCardFromQueue()
        updateUIModel()
    }

    private fun updateUIModel() {
        val currentCard = currentCard ?: return
        _uiModel.value = mapper.toUIModel(currentCard, showBack)
    }
}
