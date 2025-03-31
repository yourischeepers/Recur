package me.partypronl.recur.presentation.decks.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import me.partypronl.recur.domain.decks.ObserveDeck
import me.partypronl.recur.util.coroutines.launchCatchingOnIO
import me.partypronl.recur.util.mvvm.MutableEventFlow
import org.koin.android.annotation.KoinViewModel
import org.koin.core.annotation.InjectedParam

@KoinViewModel
class EditDeckViewModel(
    @InjectedParam private val args: EditDeckArgs,
    private val mapper: EditDeckUIMapper,
    private val observeDeck: ObserveDeck,
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

    private fun startObservingDeck() = viewModelScope.launchCatchingOnIO {
        observeDeck(args.deck.id)
            .collectLatest {
                _uiModel.value = mapper.toUIModel(it)
            }
    }
}
