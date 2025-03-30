package me.partypronl.recur

import app.cash.sqldelight.db.SqlDriver
import me.partypronl.recur.data.local.AppDatabase
import me.partypronl.recur.data.local.database.SqlDriverFactory
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
@ComponentScan("me.partypronl.recur")
class KoinApplicationModule {

    @Single
    fun provideSqlDriver() = SqlDriverFactory().createSqlDriver("recur_database")

    @Single
    fun provideAppDatabase(sqlDriver: SqlDriver) = AppDatabase(sqlDriver)

    @Factory
    fun provideDeckQueries(appDatabase: AppDatabase) = appDatabase.deckQueries

    @Factory
    fun provideFlashCardQueries(appDatabase: AppDatabase) = appDatabase.flashCardQueries
}
