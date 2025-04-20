package me.partypronl.recur.presentation.practice.quick

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import me.partypronl.recur.domain.decks.ObserveDecks
import me.partypronl.recur.domain.decks.model.Deck
import me.partypronl.recur.domain.decks.model.toCardsToPractice
import me.partypronl.recur.presentation.practice.quick.model.QuickPracticeUIModel
import me.partypronl.recur.util.coroutines.launchCatchingOnIO
import me.partypronl.recur.util.mvvm.MutableEventFlow
import me.partypronl.recur.util.mvvm.TypedUIState
import me.partypronl.recur.util.mvvm.setError
import me.partypronl.recur.util.mvvm.setNormal
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class QuickPracticeViewModel(
    private val observeDecks: ObserveDecks,
) : ViewModel() {

    private val _uiState = MutableStateFlow<TypedUIState<QuickPracticeUIModel, Throwable>>(TypedUIState.Loading)
    val uiState by lazy {
        getCardsToPractice()
        _uiState.asStateFlow()
    }

    private val _navigation = MutableEventFlow<QuickPracticeNavigation>()
    val navigation = _navigation.asEventFlow()

    fun onOpenDecksClicked() {
        _navigation.setEvent(QuickPracticeNavigation.OpenDecks)
    }

    fun onOpenAccountClicked() {
        _navigation.setEvent(QuickPracticeNavigation.OpenAccount)
    }

    fun onRepeatClicked() {
        repeatCards()
    }

    private var decks = emptyList<Deck>()
    private fun getCardsToPractice() = viewModelScope.launchCatchingOnIO(::onGetCardsError) {
        decks = observeDecks().first()
        val allCardsToPractice = decks.flatMap { it.getCardsToPractice() }

        if (allCardsToPractice.isNotEmpty()) {
            _uiState.setNormal(
                QuickPracticeUIModel(
                    cardsToPractice = allCardsToPractice.toCardsToPractice(),
                    isRepeating = false,
                )
            )
        } else {
            repeatCards()
        }
    }

    // TODO make this work
    private fun repeatCards() {
        val allPracticableCards = decks.flatMap { it.getAllCardsAsPracticable() }

        _uiState.setNormal(
            QuickPracticeUIModel(
                cardsToPractice = allPracticableCards.toCardsToPractice(),
                isRepeating = true,
            )
        )
    }

    private fun onGetCardsError(throwable: Throwable) {
        _uiState.setError(throwable)
    }
}
