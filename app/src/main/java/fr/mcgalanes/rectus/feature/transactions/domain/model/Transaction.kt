package fr.mcgalanes.rectus.feature.transactions.domain.model

import java.time.LocalDateTime

data class Transaction(
    val thumbUrl: String?,
    val title: String,
    val date: LocalDateTime,
    val priceInDecimal: Double
)
