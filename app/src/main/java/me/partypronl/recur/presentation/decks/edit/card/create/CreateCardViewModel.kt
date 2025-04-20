package me.partypronl.recur.presentation.decks.edit.card.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import me.partypronl.recur.domain.decks.CreateCard
import me.partypronl.recur.presentation.decks.edit.card.create.model.CreateCardUIModel
import me.partypronl.recur.util.coroutines.launchCatchingOnIO
import me.partypronl.recur.util.mvvm.MutableEventFlow
import org.koin.android.annotation.KoinViewModel
import org.koin.core.annotation.InjectedParam

@KoinViewModel
class CreateCardViewModel(
    @InjectedParam private val args: CreateCardArgs,
    private val createCard: CreateCard,
) : ViewModel() {

    private val _uiModel = MutableStateFlow(CreateCardUIModel())
    val uiModel = _uiModel.asStateFlow()

    private val _navigation = MutableEventFlow<CreateCardNavigation>()
    val navigation = _navigation.asEventFlow()

    fun onBackClicked() {
        _navigation.setEvent(CreateCardNavigation.GoBack)
        resetInput()
    }

    fun setFrontInput(value: String) {
        _uiModel.value = _uiModel.value.copy(frontInput = value)
        updateCanCreate()
    }

    fun setBackInput(value: String) {
        _uiModel.value = _uiModel.value.copy(backInput = value)
        updateCanCreate()
    }

    fun onCreateClicked() = viewModelScope.launchCatchingOnIO {
        _uiModel.value = _uiModel.value.copy(isCreating = true)

        createCard(
            deck = args.deck,
            front = _uiModel.value.frontInput,
            back = _uiModel.value.backInput,
        )

        _uiModel.value = _uiModel.value.copy(isCreating = false)
        _navigation.setEvent(CreateCardNavigation.GoBack)
        resetInput()
    }

    private fun resetInput() {
        _uiModel.value = CreateCardUIModel()
    }

    private fun updateCanCreate() {
        var canCreate = true
        if (_uiModel.value.frontInput.isBlank()) canCreate = false
        if (_uiModel.value.backInput.isBlank()) canCreate = false

        _uiModel.value = _uiModel.value.copy(canCreateCard = canCreate)
    }
}
