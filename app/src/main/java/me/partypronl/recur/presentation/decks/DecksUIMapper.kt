package me.partypronl.recur.presentation.decks

import me.partypronl.recur.domain.decks.model.Deck
import me.partypronl.recur.presentation.decks.model.DeckUIModel
import me.partypronl.recur.presentation.decks.model.DecksUIModel
import org.koin.core.annotation.Factory

@Factory
class DecksUIMapper {

    fun toUIModel(decks: List<Deck>): DecksUIModel {
        return DecksUIModel(
            amountOfDecks = decks.size,
            decks = decks.map(::toUIModel),
        )
    }

    private fun toUIModel(deck: Deck): DeckUIModel {
        return DeckUIModel(
            deck = deck,
            name = deck.name,
            knowledgePercentage = deck.knowledgePercentage,
            amountOfCards = deck.cards.size,
            previewText = deck.cards.joinToString(" * ") { it.front } // TODO find different character
        )
    }
}
