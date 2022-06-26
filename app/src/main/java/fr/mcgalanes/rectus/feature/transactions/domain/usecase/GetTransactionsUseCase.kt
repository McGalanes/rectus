package fr.mcgalanes.rectus.feature.transactions.domain.usecase

import fr.mcgalanes.rectus.feature.transactions.domain.model.Transaction
import fr.mcgalanes.rectus.feature.transactions.domain.repository.TransactionsRepository
import javax.inject.Inject

class GetTransactionsUseCase
@Inject constructor(
    private val repository: TransactionsRepository,
) {
    suspend operator fun invoke(): Result<List<Transaction>> = repository.getTransactions()
}
