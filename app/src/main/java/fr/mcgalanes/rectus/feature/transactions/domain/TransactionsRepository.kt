package fr.mcgalanes.rectus.feature.transactions.domain

import fr.mcgalanes.rectus.feature.transactions.data.entity.TransactionEntity

interface TransactionsRepository {
    suspend fun getTransactions(): Result<List<TransactionEntity>>
}