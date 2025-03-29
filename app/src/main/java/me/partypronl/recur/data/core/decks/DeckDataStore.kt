package me.partypronl.recur.data.core.decks

import kotlinx.coroutines.flow.Flow
import me.partypronl.recur.domain.decks.model.Deck

interface DeckDataStore {

    fun getDecks(): Flow<List<Deck>>

}
