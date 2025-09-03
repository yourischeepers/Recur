package me.partypronl.recur.presentation.account

import androidx.lifecycle.ViewModel
import me.partypronl.recur.util.mvvm.MutableEventFlow
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class AccountViewModel : ViewModel() {

    private val _navigation = MutableEventFlow<AccountNavigation>()
    val navigation = _navigation.asEventFlow()

    fun onOpenPracticeClicked() {
        _navigation.setEvent(AccountNavigation.OpenPractice)
    }

    fun onOpenDecksClicked() {
        _navigation.setEvent(AccountNavigation.OpenDecks)
    }
}
