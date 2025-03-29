package me.partypronl.recur.presentation.decks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import me.partypronl.recur.domain.decks.ObserveDecks
import me.partypronl.recur.presentation.decks.model.DecksUIModel
import me.partypronl.recur.util.coroutines.launchCatchingOnIO
import me.partypronl.recur.util.mvvm.MutableEventFlow
import me.partypronl.recur.util.mvvm.TypedUIState
import me.partypronl.recur.util.mvvm.setError
import me.partypronl.recur.util.mvvm.setNormal
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class DecksViewModel(
    private val observeDecks: ObserveDecks,
    private val mapper: DecksUIMapper,
) : ViewModel() {

    private val _uiState = MutableStateFlow<TypedUIState<DecksUIModel, Throwable>>(TypedUIState.Loading)
    val uiState by lazy {
        startObservingDecks()
        _uiState.asStateFlow()
    }

    private val _navigation = MutableEventFlow<DecksNavigation>()
    val navigation = _navigation.asEventFlow()

    fun onOpenPracticeClicked() {
        _navigation.setEvent(DecksNavigation.OpenPractice)
    }

    fun onOpenAccountClicked() {
        _navigation.setEvent(DecksNavigation.OpenAccount)
    }

    private fun startObservingDecks() = viewModelScope.launchCatchingOnIO(::onObserveDecksError) {
        observeDecks().collectLatest {
            _uiState.setNormal(mapper.toUIModel(it))
        }
    }

    private fun onObserveDecksError(throwable: Throwable) {
        _uiState.setError(throwable)
    }
}
