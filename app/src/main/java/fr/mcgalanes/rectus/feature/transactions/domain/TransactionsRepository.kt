package fr.mcgalanes.rectus.feature.transactions.domain

import fr.mcgalanes.rectus.feature.transactions.repository.entity.TransactionEntity

interface TransactionsRepository {
    suspend fun getTransactions(): List<TransactionEntity>
}