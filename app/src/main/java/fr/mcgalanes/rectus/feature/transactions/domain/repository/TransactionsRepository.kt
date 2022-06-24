package fr.mcgalanes.rectus.feature.transactions.domain.repository

import fr.mcgalanes.rectus.feature.transactions.domain.model.Transaction

interface TransactionsRepository {
    suspend fun getTransactions(): Result<List<Transaction>>
}