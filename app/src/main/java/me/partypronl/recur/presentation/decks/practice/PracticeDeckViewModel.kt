package me.partypronl.recur.presentation.decks.practice

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import me.partypronl.recur.util.mvvm.MutableEventFlow
import org.koin.android.annotation.KoinViewModel
import org.koin.core.annotation.InjectedParam

@KoinViewModel
class PracticeDeckViewModel(
    @InjectedParam private val args: PracticeDeckArgs,
) : ViewModel() {

    private val _cardsToPractice = MutableStateFlow(args.deck.getCardsToPractice())
    val cardsToPractice = _cardsToPractice.asStateFlow()

    private val _navigation = MutableEventFlow<PracticeDeckNavigation>()
    val navigation = _navigation.asEventFlow()

    fun onBackClicked() {
        _navigation.setEvent(PracticeDeckNavigation.GoBack)
    }
}
