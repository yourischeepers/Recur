package me.partypronl.recur.data.core.decks.json

import me.partypronl.recur.data.core.decks.json.model.CardsJson
import me.partypronl.recur.domain.decks.model.FlashCard
import org.koin.core.annotation.Factory
import java.util.UUID

@Factory
class CardsJsonMapper {

    fun fromJson(json: CardsJson): List<FlashCard> {
        return json.cards.map {
            FlashCard(
                id = UUID.randomUUID(),
                front = it.front,
                back = it.back,
            )
        }
    }
}
