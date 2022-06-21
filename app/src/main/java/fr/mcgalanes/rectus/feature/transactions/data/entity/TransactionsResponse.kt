package fr.mcgalanes.rectus.feature.transactions.data.entity

import com.squareup.moshi.Json

data class TransactionsResponse(
    @Json(name = "transactions") val transactions: List<TransactionEntity>
)
