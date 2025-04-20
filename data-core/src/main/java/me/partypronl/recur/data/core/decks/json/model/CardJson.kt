package me.partypronl.recur.data.core.decks.json.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CardJson(

    @SerialName("front")
    val front: String,

    @SerialName("back")
    val back: String,
)
