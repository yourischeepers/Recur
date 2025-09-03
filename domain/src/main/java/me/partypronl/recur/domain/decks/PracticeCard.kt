package me.partypronl.recur.domain.decks

import kotlinx.coroutines.flow.first
import me.partypronl.recur.domain.account.streak.AddPracticedCardToStreak
import me.partypronl.recur.domain.decks.data.DeckRepository
import me.partypronl.recur.domain.decks.model.CardToPractice
import me.partypronl.recur.domain.decks.model.FlashCardPracticeResult
import org.koin.core.annotation.Factory

@Factory
class PracticeCard(
    private val deckRepository: DeckRepository,
    private val addPracticedCardToStreak: AddPracticedCardToStreak,
) {

    suspend operator fun invoke(
        practicable: CardToPractice,
        result: FlashCardPracticeResult,
    ) {
        val decks = deckRepository.observeDecks().first()
        val deck = decks.first { deck ->
            deck.cards.any { it.id == practicable.card.id }
        }

        val newCard = if (practicable.reversed) {
            practicable.card.copy(
                reverseResult = practicable.card.reverseResult.getPracticed(result),
            )
        } else {
            practicable.card.copy(
                normalResult = practicable.card.normalResult.getPracticed(result),
            )
        }

        val newCards = deck.cards.toMutableList()
        newCards.replaceAll {
            if (it.id == newCard.id) newCard else it
        }
        val newDeck = deck.copy(cards = newCards)
        deckRepository.updateDeck(newDeck)

        addPracticedCardToStreak()
    }
}
