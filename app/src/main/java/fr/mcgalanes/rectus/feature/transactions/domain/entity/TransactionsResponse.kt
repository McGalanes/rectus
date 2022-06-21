package fr.mcgalanes.rectus.feature.transactions.domain.entity

import com.squareup.moshi.Json

data class TransactionsResponse(
    @Json(name = "transactions") val transactions: List<TransactionEntity>
)
