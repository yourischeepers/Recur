package me.partypronl.recur.data.local.util

import app.cash.sqldelight.Query
import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOne
import app.cash.sqldelight.coroutines.mapToOneOrNull
import kotlinx.coroutines.Dispatchers

private val MappingDispatcher = Dispatchers.Default

fun <T : Any> Query<T>.asFlowOfList() = asFlow().mapToList(MappingDispatcher)

fun <T : Any> Query<T>.asFlowOfOneOrNull() = asFlow().mapToOneOrNull(MappingDispatcher)

fun <T : Any> Query<T>.asFlowOfOne() = asFlow().mapToOne(MappingDispatcher)
