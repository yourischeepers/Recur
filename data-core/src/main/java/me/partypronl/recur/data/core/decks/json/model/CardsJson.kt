package me.partypronl.recur.data.core.decks.json.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CardsJson(

    @SerialName("cards")
    val cards: List<CardJson>,
)
