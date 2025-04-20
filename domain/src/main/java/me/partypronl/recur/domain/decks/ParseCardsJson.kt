package me.partypronl.recur.domain.decks

import me.partypronl.recur.domain.decks.data.DeckRepository
import me.partypronl.recur.domain.decks.exception.ParseCardsFailedException
import me.partypronl.recur.domain.decks.model.FlashCard
import org.koin.core.annotation.Factory

@Factory
class ParseCardsJson(
    private val deckRepository: DeckRepository,
) {

    operator fun invoke(json: String): List<FlashCard> {
        try {
            return deckRepository.parseCardsJson(json)
        } catch (_: Exception) {
            throw ParseCardsFailedException()
        }
    }
}
