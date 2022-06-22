package fr.mcgalanes.rectus.feature.transactions.domain.entity

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TransactionsResponse(
    @Json(name = "transactions") val transactions: List<TransactionEntity>
)
