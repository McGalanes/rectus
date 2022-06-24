package fr.mcgalanes.rectus.feature.transactions.data.repository

import fr.mcgalanes.rectus.feature.transactions.data.datasource.remote.TransactionRemoteDataSource
import fr.mcgalanes.rectus.feature.transactions.domain.model.Transaction
import fr.mcgalanes.rectus.feature.transactions.domain.repository.TransactionsRepository
import javax.inject.Inject

class TransactionRepositoryImpl @Inject constructor(
    private val remote: TransactionRemoteDataSource
) : TransactionsRepository {

    override suspend fun getTransactions(): Result<List<Transaction>> = remote.getTransactions()
}
