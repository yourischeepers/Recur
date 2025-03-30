package me.partypronl.recur.util.compose

import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier

@Stable
fun Modifier.conditionalModifier(condition: Boolean, modifier: Modifier.() -> Modifier): Modifier =
    then(if (condition) modifier() else Modifier)
