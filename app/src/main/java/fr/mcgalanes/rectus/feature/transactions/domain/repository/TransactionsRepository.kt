package fr.mcgalanes.rectus.feature.transactions.domain.repository

import fr.mcgalanes.rectus.feature.transactions.domain.entity.TransactionEntity

interface TransactionsRepository {
    suspend fun getTransactions(): Result<List<TransactionEntity>>
}