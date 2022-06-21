package fr.mcgalanes.rectus.feature.transactions.data.repository

import fr.mcgalanes.rectus.feature.transactions.data.remote.TransactionApiService
import fr.mcgalanes.rectus.feature.transactions.domain.entity.TransactionEntity
import fr.mcgalanes.rectus.feature.transactions.domain.entity.TransactionsResponse
import fr.mcgalanes.rectus.feature.transactions.domain.repository.TransactionsRepository
import javax.inject.Inject

class TransactionRepositoryImpl @Inject constructor(
    private val service: TransactionApiService
) : TransactionsRepository {

    override suspend fun getTransactions(): Result<List<TransactionEntity>> =
        service.getTransactions().map(TransactionsResponse::transactions)
}
