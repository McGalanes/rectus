package fr.mcgalanes.rectus.feature.transactions.data.datasource.remote

import fr.mcgalanes.rectus.feature.transactions.data.datasource.remote.entity.TransactionEntity
import fr.mcgalanes.rectus.feature.transactions.domain.mapper.toModel
import fr.mcgalanes.rectus.feature.transactions.domain.model.Transaction
import javax.inject.Inject

class TransactionRemoteDataSource @Inject constructor(
    private val apiService: TransactionApiService
) {
    suspend fun getTransactions(): Result<List<Transaction>> {
        return apiService.getTransactions().map { it.transactions.map(TransactionEntity::toModel) }
    }
}