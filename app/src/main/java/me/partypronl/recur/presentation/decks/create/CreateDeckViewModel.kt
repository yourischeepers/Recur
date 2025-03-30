package me.partypronl.recur.presentation.decks.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import me.partypronl.recur.domain.decks.CreateDeck
import me.partypronl.recur.presentation.decks.create.model.CreateDeckUIModel
import me.partypronl.recur.util.coroutines.launchCatchingOnIO
import me.partypronl.recur.util.mvvm.MutableEventFlow
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class CreateDeckViewModel(
    private val createDeck: CreateDeck,
) : ViewModel() {

    private val _uiModel = MutableStateFlow(CreateDeckUIModel())
    val uiModel = _uiModel.asStateFlow()

    private val _navigation = MutableEventFlow<CreateDeckNavigation>()
    val navigation = _navigation.asEventFlow()

    fun setNameInput(value: String) {
        _uiModel.value = _uiModel.value.copy(
            nameInput = value,
            canCreateDeck = value.isNotBlank(),
        )
    }

    fun onCreateClicked() = viewModelScope.launchCatchingOnIO {
        _uiModel.value = _uiModel.value.copy(isCreating = true)
        createDeck(_uiModel.value.nameInput)
        _navigation.setEvent(CreateDeckNavigation.GoBack)
    }

    fun onBackClicked() {
        _navigation.setEvent(CreateDeckNavigation.GoBack)
    }
}
