package me.partypronl.recur.util

import androidx.compose.runtime.*
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.atomicfu.atomic
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Stable
class MutableEventFlow<T : Any> private constructor(
    private val backingFlow: MutableStateFlow<Event<T>?>,
) : MutableStateFlow<Event<T>?> by backingFlow {

    constructor() : this(MutableStateFlow(null))

    fun setEvent(data: T) {
        value = Event(data)
    }

    fun asEventFlow() = EventFlow(this)
}

@Stable
class EventFlow<out T : Any> constructor(
    private val mutable: MutableEventFlow<T>,
) : StateFlow<Event<T>?> by mutable {

    suspend fun retrieveEach(collector: suspend (T?) -> Unit) {
        mutable.collectLatest { collector(it?.retrieve()) }
    }
}

@Immutable
data class Event<out T : Any>(
    private val data: T,
) {

    private val _isRetrieved = atomic(false)
    val isRetrieved: Boolean
        get() = _isRetrieved.value

    fun peek(): T {
        return data
    }

    fun retrieve(): T? {
        return if (!_isRetrieved.getAndSet(true)) data else null
    }

    override fun hashCode(): Int {
        var result = data.hashCode()
        result = 31 * result + _isRetrieved.hashCode()
        return result
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Event<*>

        if (data != other.data) return false
        if (isRetrieved != other.isRetrieved) return false

        return true
    }
}

@Composable
fun <T : Any> EventFlow<T>.RetrieveAsEffect(
    vararg keys: Any?,
    collector: suspend (T) -> Unit,
) {
    val eventState by collectAsStateWithLifecycle(null)
    val updatedCollector by rememberUpdatedState(collector)
    val scope = rememberCoroutineScope()

    eventState?.let { event ->
        LaunchedEffect(*keys, event) {
            event.retrieve()?.let {
                scope.launch { updatedCollector(it) }
            }
        }
    }
}
