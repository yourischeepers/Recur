package me.partypronl.recur.domain.decks.data

import kotlinx.coroutines.flow.Flow
import me.partypronl.recur.domain.decks.model.Deck

interface DeckRepository {

    fun observeDecks(): Flow<List<Deck>>
}
