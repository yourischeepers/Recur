package me.partypronl.recur.presentation.decks.practice

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import me.partypronl.recur.domain.decks.model.toCardsToPractice
import me.partypronl.recur.presentation.decks.practice.model.PracticeDeckUIModel
import me.partypronl.recur.util.mvvm.MutableEventFlow
import org.koin.android.annotation.KoinViewModel
import org.koin.core.annotation.InjectedParam

@KoinViewModel
class PracticeDeckViewModel(
    @InjectedParam private val args: PracticeDeckArgs,
) : ViewModel() {

    private val _cardsToPractice = MutableStateFlow(
        args.deck.getCardsToPractice()
            .ifEmpty { args.deck.getAllCardsAsPracticable() }
            .toCardsToPractice()
    )
    val cardsToPractice = _cardsToPractice.asStateFlow()

    private val _uiModel = MutableStateFlow(PracticeDeckUIModel(
        deckName = args.deck.name,
        isRepeating = args.deck.getCardsToPractice().isEmpty()
    ))
    val uiModel = _uiModel.asStateFlow()

    private val _navigation = MutableEventFlow<PracticeDeckNavigation>()
    val navigation = _navigation.asEventFlow()

    fun onBackClicked() {
        _navigation.setEvent(PracticeDeckNavigation.GoBack)
    }

    fun onRepeatWholeDeckClicked() {
        _cardsToPractice.value = args.deck.getAllCardsAsPracticable().toCardsToPractice()
        _uiModel.update { it.copy(isRepeating = true) }
    }
}
