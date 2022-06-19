package fr.mcgalanes.rectus.feature.transactions.ui.model

import java.time.LocalDate

data class Transaction(
    val thumbUrl: String,
    val title: String,
    val date: LocalDate,
    val priceInDecimal: Double
)
