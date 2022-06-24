package fr.mcgalanes.rectus.feature.transactions.data.datasource.remote.entity

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TransactionsResponse(
    @Json(name = "transactions") val transactions: List<TransactionEntity>
)
