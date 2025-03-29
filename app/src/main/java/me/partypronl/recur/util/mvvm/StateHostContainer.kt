package me.partypronl.recur.util.mvvm

import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import me.partypronl.recur.util.mvvm.TypedUIState.Error
import me.partypronl.recur.util.mvvm.TypedUIState.Loading
import me.partypronl.recur.util.mvvm.TypedUIState.Normal

private const val StateCrossFadeLabel = "State crossfade"

@Composable
fun <D, E> StateHostContainer(
    state: TypedUIState<D, E>,
    errorContent: @Composable BoxScope.(E) -> Unit,
    loadingContent: @Composable BoxScope.() -> Unit,
    normalContent: @Composable BoxScope.(D) -> Unit,
    modifier: Modifier = Modifier,
    animationSpec: FiniteAnimationSpec<Float> = tween(),
) = StateCrossfade(
    modifier = modifier,
    targetState = state,
    animationSpec = animationSpec,
    label = StateCrossFadeLabel,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        when (it) {
            is Normal -> normalContent(it.data)
            Loading -> loadingContent()
            is Error -> errorContent(it.data)
        }
    }
}
