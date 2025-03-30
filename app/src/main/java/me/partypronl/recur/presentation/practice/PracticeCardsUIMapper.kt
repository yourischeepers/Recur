package me.partypronl.recur.presentation.practice

import me.partypronl.recur.domain.decks.model.CardToPractice
import me.partypronl.recur.presentation.practice.model.PracticeCardsUIModel
import org.koin.core.annotation.Factory

@Factory
class PracticeCardsUIMapper {

    fun toUIModel(cardToPractice: CardToPractice, showBack: Boolean): PracticeCardsUIModel {
        return if (!cardToPractice.reversed) {
            PracticeCardsUIModel(
                frontText = cardToPractice.card.front,
                backText = if (showBack) cardToPractice.card.back else null
            )
        } else {
            PracticeCardsUIModel(
                frontText = cardToPractice.card.back,
                backText = if (showBack) cardToPractice.card.front else null
            )
        }
    }
}
