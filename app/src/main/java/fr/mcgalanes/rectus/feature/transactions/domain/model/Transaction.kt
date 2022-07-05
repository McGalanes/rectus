package fr.mcgalanes.rectus.feature.transactions.domain.model

import android.os.Parcelable
import java.time.LocalDateTime
import kotlinx.parcelize.Parcelize

@Parcelize
data class Transaction(
    val thumbUrl: String?,
    val title: String,
    val date: LocalDateTime,
    val priceInDecimal: Double
) : Parcelable
