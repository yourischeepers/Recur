package me.partypronl.recur.data.local.util

import app.cash.sqldelight.SuspendingTransacter
import app.cash.sqldelight.SuspendingTransactionWithoutReturn

data class MultiTransactionScope(
    private val transactions: List<SuspendingTransactionWithoutReturn>,
) : SuspendingTransactionWithoutReturn {

    override fun afterCommit(function: () -> Unit) {
        for (transaction in transactions) transaction.afterCommit(function)
    }

    override fun afterRollback(function: () -> Unit) {
        for (transaction in transactions) transaction.afterRollback(function)
    }

    override fun rollback(): Nothing {
        val errors = mutableListOf<Throwable>()
        for (transaction in transactions) {
            try {
                transaction.rollback()
            } catch (error: Throwable) {
                println("Rollback transaction failed")
                error.printStackTrace()
                errors += error
            }
        }

        throw MultiCauseException("Multiple transactions failed!", errors)
    }

    override suspend fun transactionWithResult(body: suspend SuspendingTransactionWithoutReturn.() -> Unit) {
        for (transaction in transactions) transaction.transactionWithResult(body)
    }
}

suspend fun transactionsOn(
    vararg transactors: SuspendingTransacter,
    transaction: suspend MultiTransactionScope.() -> Unit,
) {
    nestTransactions(transactors.toList(), transaction)
}

private suspend fun nestTransactions(
    transactors: List<SuspendingTransacter>,
    transaction: suspend MultiTransactionScope.() -> Unit,
    transactions: List<SuspendingTransactionWithoutReturn> = emptyList(),
) {
    if (transactors.isEmpty()) {
        val scope = MultiTransactionScope(transactions)
        transaction(scope)
    } else {
        transactors.first().transaction {
            nestTransactions(
                transactors = transactors.drop(1),
                transaction = transaction,
                transactions = transactions + this,
            )
        }
    }
}
