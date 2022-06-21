package fr.mcgalanes.rectus.feature.transactions.domain.usecase

import fr.mcgalanes.rectus.feature.transactions.domain.repository.TransactionsRepository
import fr.mcgalanes.rectus.feature.transactions.domain.entity.TransactionEntity
import fr.mcgalanes.rectus.feature.transactions.domain.mapper.toModel
import fr.mcgalanes.rectus.feature.transactions.domain.model.Transaction
import javax.inject.Inject

class GetTransactionsUseCase @Inject constructor(
    private val repository: TransactionsRepository,
) {
    suspend operator fun invoke(): Result<List<Transaction>> =
        repository
            .getTransactions()
            .mapCatching { it.map(TransactionEntity::toModel) }
}
