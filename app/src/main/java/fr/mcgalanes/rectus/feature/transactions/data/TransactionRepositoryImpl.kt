package fr.mcgalanes.rectus.feature.transactions.data

import fr.mcgalanes.rectus.feature.transactions.data.entity.TransactionEntity
import fr.mcgalanes.rectus.feature.transactions.data.entity.TransactionsResponse
import fr.mcgalanes.rectus.feature.transactions.domain.TransactionsRepository
import javax.inject.Inject

class TransactionRepositoryImpl @Inject constructor(
    private val service: TransactionApiService
) : TransactionsRepository {

    override suspend fun getTransactions(): Result<List<TransactionEntity>> =
        service.getTransactions().map(TransactionsResponse::transactions)
}
