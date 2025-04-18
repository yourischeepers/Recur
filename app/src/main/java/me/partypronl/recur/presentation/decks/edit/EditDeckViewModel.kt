package me.partypronl.recur.presentation.decks.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import me.partypronl.recur.domain.decks.DeleteCard
import me.partypronl.recur.domain.decks.DeleteDeck
import me.partypronl.recur.domain.decks.ObserveDeck
import me.partypronl.recur.presentation.decks.edit.model.EditDeckCardUIModel
import me.partypronl.recur.util.coroutines.launchCatchingOnIO
import me.partypronl.recur.util.mvvm.MutableEventFlow
import org.koin.android.annotation.KoinViewModel
import org.koin.core.annotation.InjectedParam

@KoinViewModel
class EditDeckViewModel(
    @InjectedParam private val args: EditDeckArgs,
    private val mapper: EditDeckUIMapper,
    private val observeDeck: ObserveDeck,
    private val deleteCard: DeleteCard,
    private val deleteDeck: DeleteDeck,
) : ViewModel() {

    private val _uiModel = MutableStateFlow(mapper.toUIModel(args.deck))
    val uiModel by lazy {
        startObservingDeck()
        _uiModel.asStateFlow()
    }

    private val _navigation = MutableEventFlow<EditDeckNavigation>()
    val navigation = _navigation.asEventFlow()

    fun onBackClicked() {
        _navigation.setEvent(EditDeckNavigation.GoBack)
    }

    fun onDeleteCardClicked(card: EditDeckCardUIModel) {
        _uiModel.update { it.copy(cardToDelete = card) }
    }

    fun onDismissDeleteCard() {
        _uiModel.update { it.copy(cardToDelete = null) }
    }

    fun onDeleteCardConfirm() {
        val cardToDelete = _uiModel.value.cardToDelete?.card ?: return

        viewModelScope.launchCatchingOnIO {
            _uiModel.update { it.copy(isDeletingCard = true) }
            deleteCard(args.deck, cardToDelete)
            _uiModel.update { it.copy(cardToDelete = null, isDeletingCard = false) }
        }
    }

    fun onDeleteDeckClicked() {
        _uiModel.update { it.copy(deleteDeckDialogOpen = true) }
    }

    fun onDismissDeleteDeck() {
        _uiModel.update { it.copy(deleteDeckDialogOpen = false) }
    }

    fun onDeleteDeckConfirm() = viewModelScope.launchCatchingOnIO {
        _uiModel.update { it.copy(isDeletingDeck = true) }
        deleteDeck(args.deck)
        _uiModel.update { it.copy(deleteDeckDialogOpen = false, isDeletingDeck = false) }
        _navigation.setEvent(EditDeckNavigation.GoBack)
    }

    private fun startObservingDeck() = viewModelScope.launchCatchingOnIO {
        observeDeck(args.deck.id)
            .collectLatest {
                _uiModel.value = mapper.toUIModel(it)
            }
    }
}
