package me.partypronl.recur.presentation.practice.quick

import androidx.lifecycle.ViewModel
import me.partypronl.recur.util.mvvm.MutableEventFlow
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class QuickPracticeViewModel(

) : ViewModel() {

    private val _navigation = MutableEventFlow<QuickPracticeNavigation>()
    val navigation = _navigation.asEventFlow()

    fun onOpenDecksClicked() {
        _navigation.setEvent(QuickPracticeNavigation.OpenDecks)
    }

    fun onOpenAccountClicked() {
        _navigation.setEvent(QuickPracticeNavigation.OpenAccount)
    }
}
