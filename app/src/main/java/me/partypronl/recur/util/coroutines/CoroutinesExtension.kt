package me.partypronl.recur.util.coroutines

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import kotlin.coroutines.cancellation.CancellationException

fun CoroutineScope.launchCatching(
    onError: (suspend (Throwable) -> Unit)? = null,
    block: suspend CoroutineScope.() -> Unit,
): Job {
    return launch {
        runCatching {
            block()
        }.onFailure {
            if (it is CancellationException) throw it else onError?.invoke(it)
        }
    }
}

fun CoroutineScope.launchCatchingOnIO(
    onError: (suspend (Throwable) -> Unit)? = null,
    block: suspend CoroutineScope.() -> Unit,
): Job {
    return (this + Dispatchers.IO).launchCatching(onError, block)
}
