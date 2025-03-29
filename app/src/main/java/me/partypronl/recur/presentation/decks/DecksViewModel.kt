package me.partypronl.recur.presentation.decks

import androidx.lifecycle.ViewModel
import me.partypronl.recur.util.MutableEventFlow
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class DecksViewModel(

) : ViewModel() {

    private val _navigation = MutableEventFlow<DecksNavigation>()
    val navigation = _navigation.asEventFlow()

    fun onOpenPracticeClicked() {
        _navigation.setEvent(DecksNavigation.OpenPractice)
    }

    fun onOpenAccountClicked() {
        _navigation.setEvent(DecksNavigation.OpenAccount)
    }
}
