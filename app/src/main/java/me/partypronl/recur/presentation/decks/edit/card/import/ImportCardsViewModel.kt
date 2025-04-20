package me.partypronl.recur.presentation.decks.edit.card.import

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import me.partypronl.recur.domain.decks.CreateCards
import me.partypronl.recur.domain.decks.ParseCardsJson
import me.partypronl.recur.domain.decks.exception.ParseCardsFailedException
import me.partypronl.recur.domain.decks.model.FlashCard
import me.partypronl.recur.presentation.decks.edit.card.import.model.ImportCardsUIModel
import me.partypronl.recur.util.coroutines.launchCatchingOnIO
import me.partypronl.recur.util.mvvm.MutableEventFlow
import org.koin.android.annotation.KoinViewModel
import org.koin.core.annotation.InjectedParam

@KoinViewModel
class ImportCardsViewModel(
    @InjectedParam private val args: ImportCardsArgs,
    private val parseCardsJson: ParseCardsJson,
    private val createCards: CreateCards,
) : ViewModel() {

    private val _uiModel = MutableStateFlow(ImportCardsUIModel())
    val uiModel = _uiModel.asStateFlow()

    private val _navigation = MutableEventFlow<ImportCardsNavigation>()
    val navigation = _navigation.asEventFlow()

    private var parsedCards: List<FlashCard>? = null

    fun onBackClicked() {
        _navigation.setEvent(ImportCardsNavigation.GoBack)
    }

    fun setInput(input: String) {
        _uiModel.update { it.copy(input = input) }
        reParse(input)
    }

    fun onClickConfirm() {
        val parsedCards = parsedCards ?: return

        viewModelScope.launchCatchingOnIO {
            _uiModel.update { it.copy(isImporting = true) }
            createCards(args.deck.id, parsedCards)

            _navigation.setEvent(ImportCardsNavigation.GoBack)
            _uiModel.value = ImportCardsUIModel()
        }
    }

    private fun reParse(input: String) {
        parsedCards = null

        try {
            parsedCards = parseCardsJson(input)
            _uiModel.update { it.copy(
                canImport = parsedCards?.isNotEmpty() == true,
                showErrorMessage = false,
            ) }
        } catch (_: ParseCardsFailedException) {
            onParseError()
        }
    }

    private fun onParseError() {
        _uiModel.update { it.copy(
            canImport = false,
            showErrorMessage = true,
        ) }
    }
}
