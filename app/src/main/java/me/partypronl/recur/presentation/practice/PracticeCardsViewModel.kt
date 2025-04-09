package me.partypronl.recur.presentation.practice

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import me.partypronl.recur.domain.decks.PracticeCard
import me.partypronl.recur.domain.decks.model.CardToPractice
import me.partypronl.recur.domain.decks.model.FlashCardPracticeResult
import me.partypronl.recur.domain.decks.model.getOrdered
import me.partypronl.recur.presentation.practice.model.PracticeCardsUIState
import me.partypronl.recur.util.coroutines.launchCatchingOnIO
import org.koin.android.annotation.KoinViewModel
import org.koin.core.annotation.InjectedParam

@KoinViewModel
class PracticeCardsViewModel(
    @InjectedParam private val args: PracticeCardsArgs,
    private val mapper: PracticeCardsUIMapper,
    private val practiceCard: PracticeCard,
) : ViewModel() {

    private val cardQueue = args.cardsToPractice.getOrdered().toMutableList()
    private var currentCard = pickFirstCardFromQueue()
    private var showBack = false

    private val _uiState = MutableStateFlow(
        currentCard?.let { mapper.toUIModel(it, showBack) }?.let { PracticeCardsUIState.Practicing(it) }
            ?: PracticeCardsUIState.Finished
    )
    val uiState = _uiState.asStateFlow()

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
        val practicedCard = currentCard ?: return

        viewModelScope.launchCatchingOnIO {
            if (args.trackResult) practiceCard(practicedCard, result)

            currentCard = pickFirstCardFromQueue()

            if (currentCard != null) {
                showBack = false
                updateUIModel()
            } else {
                _uiState.value = PracticeCardsUIState.Finished
            }
        }
    }

    private fun updateUIModel() {
        val currentCard = currentCard ?: return
        _uiState.value = PracticeCardsUIState.Practicing(mapper.toUIModel(currentCard, showBack))
    }
}
