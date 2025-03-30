package me.partypronl.recur.data.local.database

import android.content.Context
import app.cash.sqldelight.async.coroutines.synchronous
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import me.partypronl.recur.data.local.AppDatabase
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SqlDriverFactory : KoinComponent {

    private val context by inject<Context>()

    fun createSqlDriver(name: String): SqlDriver {
        val schema = AppDatabase.Schema.synchronous()
        return AndroidSqliteDriver(
            schema = schema,
            context = context,
            name = name,
        )
    }
}
